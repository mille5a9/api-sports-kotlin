package io.github.mille5a9.apisports.nba

import io.github.mille5a9.apisports.core.ApiResponse
import io.github.mille5a9.apisports.nba.model.Player
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class PlayersTest {
    private val expected: ApiResponse<List<Player>> = ApiResponse(
        "players/",
        mapOf("id" to "553"),
        emptyMap(),
        1,
        listOf(
            Player(
                553,
                "Lou",
                "Williams",
                Player.Birth("1986-10-27", "USA"),
                Player.Nba(2005, 16),
                Player.Height("6", "2", "1.88"),
                Player.Weight("175", "79.4"),
                "South Gwinnett HS (GA)",
                "South Gwinnett HS (GA)/USA",
                Player.Leagues(
                    standard = Player.League(6, true, "G"),
                    africa = null,
                    sacramento = null,
                    vegas = null,
                    utah = null,
                    orlando = null,
                ),
            ),
        ),
    )

    @Test
    fun playersReturns200() = runTest {
        val apiNbaClient = mockNbaClient(fixture("players.json"))
        val response = apiNbaClient.getPlayers(NbaParameters(id = 553))
        assertEquals(expected, response)
    }
}
