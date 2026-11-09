package io.github.mille5a9.apisports.nba

import io.ktor.client.request.HttpRequestData
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

/** Verifies the URL, path and query string each client method produces. */
class RequestTest {
    private val emptyResponse = """{"get":"x","parameters":[],"errors":[],"results":0,"response":[]}"""
    private val requests = mutableListOf<HttpRequestData>()
    private val client = mockNbaClient(emptyResponse, requests = requests)

    private fun lastUrl() = requests.last().url

    @Test
    fun teamByCodeSendsCode() = runTest {
        client.getTeamByCode("BOS")
        assertEquals("https://v2.nba.api-sports.io/teams?code=BOS", lastUrl().toString())
    }

    @Test
    fun nullParametersAreOmitted() = runTest {
        client.getGames(NbaParameters(team = 2, season = 2024))
        assertEquals(
            mapOf("team" to listOf("2"), "season" to listOf("2024")),
            lastUrl().parameters.entries().associate {
                it.key to
                    it.value
            },
        )
    }

    @Test
    fun dslBuildsTheSameRequestAsNamedArguments() = runTest {
        client.getGames {
            team = 2
            season = 2024
        }
        val dsl = lastUrl()
        client.getGames(NbaParameters(team = 2, season = 2024))
        assertEquals(lastUrl(), dsl)
    }

    @Test
    fun headToHeadJoinsTeamIds() = runTest {
        client.getGamesBetweenTwoTeams(1, 4)
        assertEquals("1-4", lastUrl().parameters["h2h"])
    }

    @Test
    fun standingsDefaultToStandardLeague() = runTest {
        client.getStandingsByConferenceAndSeason("east", 2024)
        assertEquals("standings", lastUrl().encodedPath.removePrefix("/"))
        assertEquals("standard", lastUrl().parameters["league"])
        assertEquals("east", lastUrl().parameters["conference"])
    }

    @Test
    fun nestedEndpointPaths() = runTest {
        client.getTeamStatistics(1, 2024)
        assertEquals("/teams/statistics", lastUrl().encodedPath)
        client.getGameStatistics(10403)
        assertEquals("/games/statistics", lastUrl().encodedPath)
        client.getPlayersStatisticsByGame(10403)
        assertEquals("/players/statistics", lastUrl().encodedPath)
    }
}
