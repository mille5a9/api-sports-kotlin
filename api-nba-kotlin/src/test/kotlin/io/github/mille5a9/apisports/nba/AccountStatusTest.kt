package io.github.mille5a9.apisports.nba

import io.github.mille5a9.apisports.core.AccountStatus
import io.github.mille5a9.apisports.core.ApiResponse
import io.github.mille5a9.apisports.core.Paging
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class AccountStatusTest {
    private val expected = ApiResponse(
        get = "status",
        results = 0,
        paging = Paging(current = 1, total = 1),
        response = AccountStatus(
            AccountStatus.Account(firstname = "John", lastname = "Smith", email = "test@gmail.com"),
            AccountStatus.Subscription(plan = "Free", end = "2024-12-20T00:00:00+00:00", active = true),
            AccountStatus.Requests(current = 0, limitDay = 100),
        ),
    )

    @Test
    fun accountStatusReturns200() = runTest {
        val apiNbaClient = mockNbaClient(fixture("status.json"))
        assertEquals(expected, apiNbaClient.getAccountStatus())
    }
}
