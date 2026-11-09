package io.github.mille5a9.apisports.nba

import io.github.mille5a9.apisports.core.ApiResponse
import io.github.mille5a9.apisports.nba.model.Standing
import io.github.mille5a9.apisports.nba.model.TeamSummary
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class StandingsTest {
    private val expected: ApiResponse<List<Standing>> = ApiResponse(
        "standings/",
        mapOf("league" to "standard", "season" to "2021", "team" to "1"),
        emptyMap(),
        1,
        listOf(
            Standing(
                "standard",
                2021,
                TeamSummary(
                    1,
                    "Atlanta Hawks",
                    "Hawks",
                    "ATL",
                    "https://upload.wikimedia.org/wikipedia/fr/e/ee/Hawks_2016.png",
                ),
                Standing.Conference(
                    "east",
                    8,
                    26,
                    26,
                ),
                Standing.Division(
                    "southeast",
                    2,
                    9,
                    7,
                    "10.0",
                ),
                Standing.Record(
                    27,
                    16,
                    43,
                    ".524",
                    7,
                ),
                Standing.Record(
                    14,
                    25,
                    39,
                    ".476",
                    3,
                ),
                "10.0",
                1,
                true,
                null,
            ),
        ),
    )

    @Test
    fun standingsReturns200() = runTest {
        val apiNbaClient = mockNbaClient(fixture("standings.json"))
        val response = apiNbaClient.getStandings(NbaParameters(league = "standard", season = 2021, team = 1))
        assertEquals(expected, response)
    }
}
