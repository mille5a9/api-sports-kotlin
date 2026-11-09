package io.github.mille5a9.apisports.nba

import io.github.mille5a9.apisports.core.ApiResponse
import io.github.mille5a9.apisports.nba.model.Game
import io.github.mille5a9.apisports.nba.model.TeamSummary
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GamesTest {
    private val expected: ApiResponse<List<Game>> = ApiResponse(
        "games/",
        mapOf("id" to "10404"),
        emptyMap(),
        1,
        listOf(
            Game(
                10404,
                "standard",
                2021,
                Game.Date(
                    "2022-02-12T00:00:00.000Z",
                    "2022-02-12T02:27:00.000Z",
                    "2:17",
                ),
                2,
                Game.Status(
                    null,
                    false,
                    3,
                    "Finished",
                ),
                Game.Periods(
                    4,
                    4,
                    false,
                ),
                Game.Arena(
                    "Gainbridge Fieldhouse",
                    "Indianapolis",
                    "IN",
                    "USA",
                ),
                Game.Teams(
                    TeamSummary(
                        7,
                        "Cleveland Cavaliers",
                        "Cavaliers",
                        "CLE",
                        "https://upload.wikimedia.org/wikipedia/fr/thumb/0/06/Cavs_de_Cleveland_logo_2017.png/150px-Cavs_de_Cleveland_logo_2017.png",
                    ),
                    TeamSummary(
                        15,
                        "Indiana Pacers",
                        "Pacers",
                        "IND",
                        "https://upload.wikimedia.org/wikipedia/fr/thumb/c/cf/Pacers_de_l%27Indiana_logo.svg/1180px-Pacers_de_l%27Indiana_logo.svg.png",
                    ),
                ),
                Game.Scores(
                    Game.Score(
                        35,
                        21,
                        Game.Series(
                            3,
                            0,
                        ),
                        listOf(
                            "28",
                            "35",
                            "25",
                            "32",
                        ),
                        120,
                    ),
                    Game.Score(
                        19,
                        38,
                        Game.Series(
                            0,
                            3,
                        ),
                        listOf(
                            "47",
                            "27",
                            "22",
                            "17",
                        ),
                        113,
                    ),
                ),
                listOf(
                    "Ben Taylor",
                    "Dedric Taylor",
                    "Suyash Mehta",
                ),
                2,
                7,
                null,
            ),
        ),
    )

    @Test
    fun gamesReturns200() = runTest {
        val apiNbaClient = mockNbaClient(fixture("games.json"))
        val response = apiNbaClient.getGames(NbaParameters(id = 10404))
        assertEquals(expected, response)
    }
}
