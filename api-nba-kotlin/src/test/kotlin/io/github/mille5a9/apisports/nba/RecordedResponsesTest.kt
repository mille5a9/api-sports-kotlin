package io.github.mille5a9.apisports.nba

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Decodes real responses recorded from API-NBA v2 (src/test/resources/recorded) to catch fields that
 * the API sends as null or omits, which hand-written fixtures tend to miss.
 */
class RecordedResponsesTest {
    private fun recorded(name: String) =
        requireNotNull(javaClass.getResource("/recorded/$name")) { "Missing recording $name" }.readText()

    private fun client(name: String) = mockNbaClient(recorded(name))

    @Test
    fun games() = runTest {
        val response = client("games.json").getGamesByDate("2026-05-17")
        assertEquals(response.results, response.response.size)
        assertTrue(response.response.isNotEmpty())
    }

    @Test
    fun gameStatistics() = runTest {
        assertEquals(2, client("games-statistics.json").getGameStatistics(16831).response.size)
    }

    @Test
    fun leagues() = runTest {
        assertTrue("standard" in client("leagues.json").getLeagues().response)
    }

    @Test
    fun players() = runTest {
        assertEquals(
            265,
            client("players.json")
                .getPlayerById(265)
                .response
                .single()
                .id,
        )
    }

    @Test
    fun playerStatistics() = runTest {
        val response = client("players-statistics.json").getPlayerStatisticsByPlayerAndSeason(265, 2024)
        assertEquals(response.results, response.response.size)
    }

    @Test
    fun seasons() = runTest {
        assertTrue(2024 in client("seasons.json").getSeasons().response)
    }

    @Test
    fun standings() = runTest {
        val response = client("standings.json").getStandings(NbaParameters(league = "standard", season = 2024))
        assertEquals(response.results, response.response.size)
    }

    @Test
    fun teamStatistics() = runTest {
        assertEquals(1, client("teams-statistics.json").getTeamStatistics(1, 2024).response.size)
    }

    @Test
    fun teams() = runTest {
        val response = client("teams.json").getAllTeams()
        assertEquals(response.results, response.response.size)
    }
}
