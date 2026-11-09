package io.github.mille5a9.apisports.nba

import io.github.mille5a9.apisports.core.ApiResponse
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class LeaguesTest {
    private val expected: ApiResponse<List<String>> = ApiResponse(
        "leagues/",
        emptyMap(),
        emptyMap(),
        6,
        listOf("africa", "orlando", "sacramento", "standard", "utah", "vegas"),
    )

    @Test
    fun leaguesReturns200() = runTest {
        val apiNbaClient = mockNbaClient(fixture("leagues.json"))
        val response = apiNbaClient.getLeagues()
        assertEquals(expected, response)
    }
}
