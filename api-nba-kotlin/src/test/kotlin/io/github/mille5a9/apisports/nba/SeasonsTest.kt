package io.github.mille5a9.apisports.nba

import io.github.mille5a9.apisports.core.ApiResponse
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class SeasonsTest {
    private val expected: ApiResponse<List<Int>> = ApiResponse(
        "seasons/",
        emptyMap(),
        emptyMap(),
        9,
        listOf(2015, 2016, 2017, 2018, 2019, 2020, 2021, 2022, 2023),
    )

    @Test
    fun seasonsReturns200() = runTest {
        val apiNbaClient = mockNbaClient(fixture("seasons.json"))
        val response = apiNbaClient.getSeasons()
        assertEquals(expected, response)
    }
}
