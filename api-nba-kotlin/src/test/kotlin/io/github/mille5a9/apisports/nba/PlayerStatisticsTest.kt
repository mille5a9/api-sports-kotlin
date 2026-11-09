package io.github.mille5a9.apisports.nba

import io.github.mille5a9.apisports.core.ApiResponse
import io.github.mille5a9.apisports.nba.model.PlayerStatistics
import io.github.mille5a9.apisports.nba.model.TeamSummary
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class PlayerStatisticsTest {
    private val expected: ApiResponse<List<PlayerStatistics>> = ApiResponse(
        "players/statistics",
        mapOf("id" to "236", "game" to "8137", "season" to "2020"),
        emptyMap(),
        1,
        listOf(
            PlayerStatistics(
                PlayerStatistics.PlayerRef(
                    236,
                    "Buddy",
                    "Hield",
                ),
                TeamSummary(
                    30,
                    "Sacramento Kings",
                    "Kings",
                    "SAC",
                    "https://upload.wikimedia.org/wikipedia/fr/thumb/9/95/Kings_de_Sacramento_logo.svg/1200px-Kings_de_Sacramento_logo.svg.png",
                ),
                PlayerStatistics.GameRef(
                    8137,
                ),
                23,
                "SG",
                "23:01",
                8,
                17,
                "47.1",
                2,
                2,
                "100",
                5,
                11,
                "45.5",
                0,
                0,
                0,
                1,
                1,
                1,
                0,
                0,
                "-14",
                null,
            ),
        ),
    )

    @Test
    fun gamesStatisticsReturns200() = runTest {
        val apiNbaClient = mockNbaClient(fixture("player-statistics.json"))
        val response = apiNbaClient.getPlayerStatistics(
            NbaParameters(id = 236, game = 8137, season = 2020),
        )
        assertEquals(expected, response)
    }
}
