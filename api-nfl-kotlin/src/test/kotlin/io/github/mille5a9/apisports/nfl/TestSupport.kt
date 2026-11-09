package io.github.mille5a9.apisports.nfl

import io.github.mille5a9.apisports.core.Host
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.request.HttpRequestData
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf

/** Reads a real API response from `src/test/resources/recorded`. */
internal fun recorded(name: String): String =
    requireNotNull(ApiNflClient::class.java.getResource("/recorded/$name")) { "Missing recording $name" }.readText()

/** An [ApiNflClient] that answers every request with [body] and records the requests it receives. */
internal fun mockNflClient(
    body: String,
    host: Host = Host.API_SPORTS,
    requests: MutableList<HttpRequestData> = mutableListOf(),
): ApiNflClient =
    ApiNflClient(
        apiKey = "test-key",
        host = host,
        engine = MockEngine { request ->
            requests += request
            respond(body, HttpStatusCode.OK, headersOf(HttpHeaders.ContentType, "application/json"))
        },
    )
