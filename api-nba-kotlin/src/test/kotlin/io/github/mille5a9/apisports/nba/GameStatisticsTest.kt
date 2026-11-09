package io.github.mille5a9.apisports.nba

import io.github.mille5a9.apisports.core.ApiResponse
import io.github.mille5a9.apisports.nba.model.GameTeamStatistics
import io.github.mille5a9.apisports.nba.model.TeamSummary
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GameStatisticsTest {
    private val expected: ApiResponse<List<GameTeamStatistics>> = ApiResponse(
        "games/statistics",
        mapOf("id" to "10403"),
        emptyMap(),
        2,
        listOf(
            GameTeamStatistics(
                TeamSummary(
                    5,
                    "Charlotte Hornets",
                    "Hornets",
                    "CHA",
                    "https://upload.wikimedia.org/wikipedia/fr/thumb/f/f3/Hornets_de_Charlotte_logo.svg/1200px-Hornets_de_Charlotte_logo.svg.png",
                ),
                listOf(
                    GameTeamStatistics.Statistics(
                        15,
                        70,
                        28,
                        18,
                        24,
                        12,
                        141,
                        54,
                        97,
                        "55.7",
                        15,
                        23,
                        "65.2",
                        18,
                        42,
                        "42.9",
                        15,
                        36,
                        51,
                        36,
                        22,
                        13,
                        18,
                        2,
                        "22",
                        "240:00",
                    ),
                ),
            ),
            GameTeamStatistics(
                TeamSummary(
                    10,
                    "Detroit Pistons",
                    "Pistons",
                    "DET",
                    "https://upload.wikimedia.org/wikipedia/commons/thumb/3/39/Logo_of_the_Detroit_Pistons.png/300px-Logo_of_the_Detroit_Pistons.png",
                ),
                listOf(
                    GameTeamStatistics.Statistics(
                        8,
                        52,
                        2,
                        13,
                        24,
                        12,
                        119,
                        48,
                        102,
                        "47.1",
                        11,
                        18,
                        "61.1",
                        12,
                        35,
                        "34.3",
                        16,
                        29,
                        45,
                        32,
                        20,
                        10,
                        18,
                        6,
                        "-22",
                        "240:00",
                    ),
                ),
            ),
        ),
    )

    @Test
    fun gamesStatisticsReturns200() = runTest {
        val apiNbaClient = mockNbaClient(fixture("game-statistics.json"))
        val response = apiNbaClient.getGameStatistics(10403)
        assertEquals(expected, response)
    }
}
