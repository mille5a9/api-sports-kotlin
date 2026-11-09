package io.github.mille5a9.apisports.nba

import io.github.mille5a9.apisports.core.ApiResponse
import io.github.mille5a9.apisports.nba.model.TeamStatistics
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class TeamStatisticsTest {
    private val expected: ApiResponse<List<TeamStatistics>> = ApiResponse(
        "teams/statistics",
        mapOf("id" to "1", "season" to "2021"),
        emptyMap(),
        1,
        listOf(
            TeamStatistics(
                94,
                894,
                4198,
                1242,
                1157,
                1494,
                1053,
                10567,
                3846,
                8246,
                "46.7",
                1675,
                2071,
                "80.7",
                1200,
                3235,
                "36.9",
                939,
                3176,
                4115,
                2280,
                1781,
                673,
                1101,
                394,
                94,
            ),
        ),
    )

    @Test
    fun gamesStatisticsReturns200() = runTest {
        val apiNbaClient = mockNbaClient(fixture("team-statistics.json"))
        val response = apiNbaClient.getTeamStatistics(1, 2021)
        assertEquals(expected, response)
    }
}
