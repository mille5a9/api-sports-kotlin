package io.github.mille5a9.apisports.core

import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.request.HttpRequestData
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.Serializable
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ApiSportsClientTest {
    @Serializable
    data class Item(
        val id: Int,
    )

    private object Things : Endpoint {
        override val path = "things"
    }

    private class TestClient(
        host: Host,
        engine: MockEngine,
    ) : ApiSportsClient(SportApi.NBA, "secret", host, engine) {
        suspend fun things(): ApiResponse<List<Item>> = get(Things)
    }

    private val requests = mutableListOf<HttpRequestData>()

    private fun client(
        body: String,
        status: HttpStatusCode = HttpStatusCode.OK,
        host: Host = Host.API_SPORTS,
    ) = TestClient(
        host,
        MockEngine { request ->
            requests += request
            respond(body, status, headersOf(HttpHeaders.ContentType, "application/json"))
        },
    )

    private val ok = """{"get":"things","parameters":[],"errors":[],"results":1,"response":[{"id":7}]}"""

    @Test
    fun apiSportsHostUsesApiSportsKeyHeader() = runTest {
        client(ok).things()
        val request = requests.single()
        assertEquals("https://v2.nba.api-sports.io/things", request.url.toString())
        assertEquals("secret", request.headers["x-apisports-key"])
        assertNull(request.headers["x-rapidapi-key"])
    }

    @Test
    fun rapidApiHostUsesRapidApiHeaders() = runTest {
        client(ok, host = Host.RAPID_API).things()
        val request = requests.single()
        assertEquals("https://api-nba-v1.p.rapidapi.com/things", request.url.toString())
        assertEquals("secret", request.headers["x-rapidapi-key"])
        assertEquals("api-nba-v1.p.rapidapi.com", request.headers["x-rapidapi-host"])
        assertNull(request.headers["x-apisports-key"])
    }

    @Test
    fun decodesEnvelopeWithEmptyArrayParameters() = runTest {
        val response = client(ok).things()
        assertEquals(ApiResponse("things", emptyMap(), emptyMap(), 1, listOf(Item(7))), response)
    }

    @Test
    fun ignoresUnknownFields() = runTest {
        val response = client(
            """{"get":"things","errors":[],"results":1,"response":[{"id":7,"new":true}],"extra":1}""",
        ).things()
        assertEquals(listOf(Item(7)), response.response)
    }

    @Test
    fun errorObjectIsThrown() = runTest {
        val body =
            """{"get":"things","parameters":[],"errors":{"token":"Error/Missing application key."},"results":0,"response":[]}"""
        val e = assertFailsWith<ApiSportsException> { client(body).things() }
        assertEquals(mapOf("token" to "Error/Missing application key."), e.errors)
        assertEquals(200, e.statusCode)
        assertTrue("Missing application key" in e.message!!)
    }

    @Test
    fun errorArrayIsThrown() = runTest {
        val body = """{"get":"things","parameters":[],"errors":["Too many requests"],"results":0,"response":[]}"""
        val e = assertFailsWith<ApiSportsException> { client(body).things() }
        assertEquals(mapOf("0" to "Too many requests"), e.errors)
    }

    @Test
    fun nonJsonErrorPageIsThrown() = runTest {
        val e = assertFailsWith<ApiSportsException> {
            client("<html>Bad Gateway</html>", HttpStatusCode.BadGateway).things()
        }
        assertEquals(502, e.statusCode)
    }

    @Test
    fun httpErrorWithoutApiErrorsIsThrown() = runTest {
        val e = assertFailsWith<ApiSportsException> {
            client("""{"message":"You are not subscribed to this API."}""", HttpStatusCode.Forbidden).things()
        }
        assertEquals(403, e.statusCode)
    }

    @Test
    fun modelMismatchIsWrapped() = runTest {
        val body = """{"get":"things","errors":[],"results":1,"response":[{"id":"not a number"}]}"""
        val e = assertFailsWith<ApiSportsException> { client(body).things() }
        assertTrue(e.message!!.startsWith("Could not decode the response from things"))
    }
}
