package io.github.mille5a9.apisports.nfl

import io.github.mille5a9.apisports.nfl.ApiNflClient.Companion.NFL
import io.github.mille5a9.apisports.nfl.model.HomeAway
import io.github.mille5a9.apisports.nfl.model.get
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

/** Decodes real API-American-Football responses and checks values that exercise each model. */
class RecordedResponsesTest {
    private fun client(name: String) = mockNflClient(recorded(name))

    @Test
    fun timezones() = runTest {
        val response = client("timezone.json").getTimezones()
        assertEquals(426, response.response.size)
        assertTrue("America/New_York" in response.response)
    }

    @Test
    fun seasons() = runTest {
        assertTrue(2024 in client("seasons.json").getSeasons().response)
    }

    @Test
    fun leaguesIncludingMisspelledCoverageField() = runTest {
        val leagues = client("leagues.json").getLeagues().response
        assertEquals(listOf("NFL", "NCAA"), leagues.map { it.league.name })
        val coverage = leagues
            .first()
            .seasons
            .first()
            .coverage
        assertNotNull(coverage?.games?.statistics, "`statisitcs` should map to statistics")
    }

    @Test
    fun teamsIncludingConferencePlaceholders() = runTest {
        val teams = client("teams.json").getTeams(league = NFL, season = 2024).response
        assertEquals(34, teams.size)
        assertNull(teams.first { it.name == "AFC" }.code)
        assertTrue(teams.any { it.code == "BUF" })
    }

    @Test
    fun player() = runTest {
        assertEquals(
            1,
            client("players.json")
                .getPlayerById(1)
                .response
                .single()
                .id,
        )
    }

    @Test
    fun playerStatistics() = runTest {
        val stats = client("players-statistics.json").getPlayerStatistics(1, 2022).response.single()
        val passing = stats.teams
            .first()
            .groups
            .first { it.name == "Passing" }
            .statistics
        assertEquals("502", passing["passing attempts"])
        assertNull(passing["passing touchdowns pct"])
    }

    @Test
    fun injuries() = runTest {
        val injuries = client("injuries.json").getInjuriesByTeam(1).response
        assertEquals(10, injuries.size)
        assertTrue(injuries.all { it.team.id == 1 })
    }

    @Test
    fun gamesWithNullQuarters() = runTest {
        val games = client("games.json").getGamesBySeason(NFL, 2024).response
        val hallOfFame = games.first { it.game.id == 13146 }
        assertEquals("Chicago Bears", hallOfFame.teams.home.name)
        assertEquals(21, hallOfFame.scores.home.total)
        assertNull(hallOfFame.scores.home.quarter4)
        assertEquals(2024, hallOfFame.league.season, "season arrives as a string in games")
    }

    @Test
    fun gameEvents() = runTest {
        val events = client("games-events.json").getGameEvents(13146).response
        assertEquals(HomeAway(home = 0, away = 7), events.first().score)
        assertEquals("TD", events.first().type)
    }

    @Test
    fun gameTeamStatisticsIncludingMisspelledFields() = runTest {
        val stats = client("games-statistics-teams.json").getGameTeamStatistics(13146).response
        assertEquals(2, stats.size)
        val bears = stats.first().statistics
        assertNotNull(bears.rushing, "`rushings` should map to rushing")
        assertNotNull(bears.possession?.total, "`posession` should map to possession")
    }

    @Test
    fun gamePlayerStatistics() = runTest {
        val teams = client("games-statistics-players.json").getGamePlayerStatistics(13146).response
        val passing = teams.first().groups.first { it.name == "Passing" }
        assertTrue(passing.players.isNotEmpty())
        assertNotNull(passing.players.first().statistics["yards"])
    }

    @Test
    fun standings() = runTest {
        val standings = client("standings.json").getStandings(NFL, 2024).response
        assertEquals(32, standings.size)
        val bills = standings.first { it.team.name == "Buffalo Bills" }
        assertEquals(13, bills.won)
        assertEquals(525, bills.points?.scored)
        assertNull(bills.ncaaConference?.won)
    }

    @Test
    fun conferencesAndDivisions() = runTest {
        assertEquals(2, client("standings-conferences.json").getConferences(NFL, 2024).response.size)
        assertEquals(8, client("standings-divisions.json").getDivisions(NFL, 2024).response.size)
    }

    @Test
    fun odds() = runTest {
        assertTrue(client("odds.json").getOdds(13146).response.isEmpty())
        assertEquals(361, client("odds-bets.json").getBetTypes().response.size)
        assertEquals(24, client("odds-bookmakers.json").getBookmakers().response.size)
    }
}
