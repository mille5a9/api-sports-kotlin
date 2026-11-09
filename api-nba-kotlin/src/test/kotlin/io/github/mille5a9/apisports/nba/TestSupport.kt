package io.github.mille5a9.apisports.nba

import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.request.HttpRequestData
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf

/** Reads a recorded API response from `src/test/resources/fixtures`. */
internal fun fixture(name: String): String =
    requireNotNull(ApiNbaClient::class.java.getResource("/fixtures/$name")) { "Missing fixture $name" }.readText()

/** An [ApiNbaClient] that answers every request with [body] and records the requests it receives. */
internal fun mockNbaClient(
    body: String,
    status: HttpStatusCode = HttpStatusCode.OK,
    requests: MutableList<HttpRequestData> = mutableListOf(),
): ApiNbaClient =
    ApiNbaClient(
        apiKey = "test-key",
        engine = MockEngine { request ->
            requests += request
            respond(body, status, headersOf(HttpHeaders.ContentType, "application/json"))
        },
    )
