package io.github.mille5a9.apisports.nfl

import io.github.mille5a9.apisports.core.Host
import io.github.mille5a9.apisports.nfl.ApiNflClient.Companion.NFL
import io.ktor.client.request.HttpRequestData
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

/** Verifies the URL, path, query string and headers each client method produces. */
class RequestTest {
    private val empty = """{"get":"x","parameters":[],"errors":[],"results":0,"response":[]}"""
    private val requests = mutableListOf<HttpRequestData>()
    private val client = mockNflClient(empty, requests = requests)

    private fun last() = requests.last()

    @Test
    fun usesAmericanFootballHost() = runTest {
        client.getGamesBySeason(NFL, 2024)
        assertEquals("https://v1.american-football.api-sports.io/games?league=1&season=2024", last().url.toString())
        assertEquals("test-key", last().headers["x-apisports-key"])
    }

    @Test
    fun rapidApiHostHeader() = runTest {
        val rapid = mockNflClient(empty, host = Host.RAPID_API, requests = requests)
        rapid.getSeasons()
        assertEquals("https://api-american-football.p.rapidapi.com/seasons", last().url.toString())
        assertEquals("api-american-football.p.rapidapi.com", last().headers["x-rapidapi-host"])
    }

    @Test
    fun optionalArgumentsAreOmitted() = runTest {
        client.getGamePlayerStatistics(13146)
        assertEquals("/games/statistics/players", last().url.encodedPath)
        assertEquals(setOf("id"), last().url.parameters.names())

        client.getGamePlayerStatistics(13146, teamId = 16, group = "Passing")
        assertEquals("16", last().url.parameters["team"])
        assertEquals("Passing", last().url.parameters["group"])
    }

    @Test
    fun dslMatchesNamedArguments() = runTest {
        client.getGames {
            league = NFL
            season = 2024
            team = 20
        }
        val dsl = last().url
        client.getGames(NflParameters(league = NFL, season = 2024, team = 20))
        assertEquals(last().url, dsl)
    }

    @Test
    fun booleanParameter() = runTest {
        client.getLeagues(NflParameters(current = true))
        assertEquals("true", last().url.parameters["current"])
    }

    @Test
    fun standingsFilters() = runTest {
        client.getStandings(NFL, 2024, division = "AFC East")
        assertEquals("AFC East", last().url.parameters["division"])
        assertEquals(setOf("league", "season", "division"), last().url.parameters.names())
    }

    @Test
    fun injuriesByPlayer() = runTest {
        client.getInjuriesByPlayer(14653)
        assertEquals("https://v1.american-football.api-sports.io/injuries?player=14653", last().url.toString())
    }
}
