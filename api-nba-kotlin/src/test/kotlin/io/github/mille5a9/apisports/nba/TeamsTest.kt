package io.github.mille5a9.apisports.nba

import io.github.mille5a9.apisports.core.ApiResponse
import io.github.mille5a9.apisports.nba.model.Team
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class TeamsTest {
    private val expected: ApiResponse<List<Team>> = ApiResponse(
        "teams/",
        mapOf("id" to "1"),
        emptyMap(),
        1,
        listOf(
            Team(
                1,
                "Atlanta Hawks",
                "Hawks",
                "ATL",
                "Atlanta",
                "https://upload.wikimedia.org/wikipedia/fr/e/ee/Hawks_2016.png",
                false,
                true,
                Team.Leagues(
                    standard = Team.League("East", "Southeast"),
                    africa = null,
                    sacramento = Team.League("East", "Southeast"),
                    vegas = Team.League("summer", null),
                    utah = Team.League("East", "Southeast"),
                    orlando = null,
                ),
            ),
        ),
    )

    @Test
    fun teamsReturns200() = runTest {
        val apiNbaClient = mockNbaClient(fixture("teams.json"))
        val response = apiNbaClient.getTeams(NbaParameters(id = 1))
        assertEquals(expected, response)
    }
}
