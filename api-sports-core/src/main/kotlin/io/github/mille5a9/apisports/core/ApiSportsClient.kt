package io.github.mille5a9.apisports.core

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.serializer
import java.io.Closeable

/**
 * Base class for the sport-specific clients. Handles the base URL, authentication headers,
 * HTTP caching, decoding of the response envelope and turning API errors into [ApiSportsException].
 *
 * Clients own an [HttpClient]; [close] them when done, e.g. with `use { }`.
 *
 * @param api The API-SPORTS product this client talks to.
 * @param apiKey Your key for [host].
 * @param host Whether to call API-SPORTS directly or through RapidAPI.
 * @param engine The Ktor engine to use. Defaults to CIO; pass a `MockEngine` in tests.
 * @param configure Extra Ktor client configuration, e.g. installing `Logging` or `HttpTimeout`.
 */
public abstract class ApiSportsClient(
    public val api: SportApi,
    apiKey: String,
    public val host: Host = Host.API_SPORTS,
    engine: HttpClientEngine? = null,
    configure: HttpClientConfig<*>.() -> Unit = {},
) : Closeable {
    private val clientConfig: HttpClientConfig<*>.() -> Unit = {
        install(HttpCache)
        val host = this@ApiSportsClient.host
        defaultRequest {
            url(api.baseUrl(host))
            when (host) {
                Host.API_SPORTS -> {
                    header(API_SPORTS_KEY_HEADER, apiKey)
                }

                Host.RAPID_API -> {
                    header(RAPID_API_KEY_HEADER, apiKey)
                    header(RAPID_API_HOST_HEADER, api.rapidApiHost)
                }
            }
        }
        configure()
    }

    @PublishedApi
    internal val httpClient: HttpClient =
        if (engine != null) HttpClient(engine, clientConfig) else HttpClient(CIO, clientConfig)

    /**
     * Retrieves the account status for the configured API key.
     * Does not count against the daily request limit.
     */
    public suspend fun getAccountStatus(): ApiResponse<AccountStatus> = get(StatusEndpoint)

    /** Calls [endpoint] and decodes the envelope with a payload of type [T]. */
    protected suspend inline fun <reified T> get(
        endpoint: Endpoint,
        params: QueryParameters? = null,
    ): ApiResponse<T> = request(endpoint, params?.toQueryMap().orEmpty(), serializer<ApiResponse<T>>())

    @PublishedApi
    internal suspend fun <T> request(
        endpoint: Endpoint,
        query: Map<String, String>,
        deserializer: KSerializer<ApiResponse<T>>,
    ): ApiResponse<T> {
        val response = httpClient.get(endpoint.path) {
            query.forEach { (key, value) -> parameter(key, value) }
        }
        val status = response.status
        val body = response.bodyAsText()

        val json: JsonObject = try {
            ApiSportsJson.parseToJsonElement(body).jsonObject
        } catch (e: IllegalArgumentException) {
            throw ApiSportsException(
                "HTTP ${status.value} from ${endpoint.path}: response was not a JSON object",
                statusCode = status.value,
                cause = e,
            )
        }

        val errors = json["errors"]
            ?.let {
                ApiSportsJson.decodeFromJsonElement(
                    LenientStringMapSerializer,
                    it,
                )
            }.orEmpty()
        if (errors.isNotEmpty()) {
            throw ApiSportsException(
                "API-SPORTS returned errors for ${endpoint.path}: " +
                    errors.entries.joinToString { "${it.key}: ${it.value}" },
                errors = errors,
                statusCode = status.value,
            )
        }
        if (!status.isSuccess()) {
            throw ApiSportsException("HTTP ${status.value} from ${endpoint.path}", statusCode = status.value)
        }

        return try {
            ApiSportsJson.decodeFromJsonElement(deserializer, json)
        } catch (e: SerializationException) {
            throw ApiSportsException(
                "Could not decode the response from ${endpoint.path}: ${e.message}",
                statusCode = status.value,
                cause = e,
            )
        }
    }

    /** Closes the underlying [HttpClient]. An engine passed to the constructor is not closed. */
    override fun close() {
        httpClient.close()
    }

    private object StatusEndpoint : Endpoint {
        override val path: String = "status"
    }

    public companion object {
        public const val API_SPORTS_KEY_HEADER: String = "x-apisports-key"
        public const val RAPID_API_KEY_HEADER: String = "x-rapidapi-key"
        public const val RAPID_API_HOST_HEADER: String = "x-rapidapi-host"
    }
}
