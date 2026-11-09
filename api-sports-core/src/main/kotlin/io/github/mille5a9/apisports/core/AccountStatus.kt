package io.github.mille5a9.apisports.core

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The payload of the `status` endpoint: account details and today's request usage.
 * Calling `status` does not count against the daily quota.
 */
@Serializable
public data class AccountStatus(
    public val account: Account,
    public val subscription: Subscription,
    public val requests: Requests,
) {
    /** The owner of the API key. */
    @Serializable
    public data class Account(
        public val firstname: String?,
        public val lastname: String?,
        public val email: String?,
    )

    /**
     * @property plan The plan name, e.g. `Free`.
     * @property end When the subscription ends, as an ISO-8601 timestamp.
     */
    @Serializable
    public data class Subscription(
        public val plan: String,
        public val end: String?,
        public val active: Boolean,
    )

    /**
     * @property current Requests made today.
     * @property limitDay The daily request limit for the plan.
     */
    @Serializable
    public data class Requests(
        public val current: Int,
        @SerialName("limit_day") public val limitDay: Int,
    )
}
