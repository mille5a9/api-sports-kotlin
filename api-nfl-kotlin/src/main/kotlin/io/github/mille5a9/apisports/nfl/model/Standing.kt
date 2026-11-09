package io.github.mille5a9.apisports.nfl.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A team's standing, from `standings`.
 *
 * @property position Rank within [division].
 * @property records Win-loss records as `"W-L"` strings.
 * @property streak Current streak, e.g. `W3` or `L1`.
 * @property ncaaConference In-conference record; NCAA only, all null for the NFL.
 */
@Serializable
public data class Standing(
    public val league: League,
    public val conference: String? = null,
    public val division: String? = null,
    public val position: Int? = null,
    public val team: TeamRef,
    public val won: Int? = null,
    public val lost: Int? = null,
    public val ties: Int? = null,
    public val points: Points? = null,
    public val records: Records? = null,
    public val streak: String? = null,
    @SerialName("ncaa_conference") public val ncaaConference: NcaaConference? = null,
) {
    @Serializable
    public data class League(
        public val id: Int,
        public val name: String,
        public val season: Int,
        public val logo: String? = null,
        public val country: Country? = null,
    )

    @Serializable
    public data class Points(
        @SerialName("for") public val scored: Int? = null,
        public val against: Int? = null,
        public val difference: Int? = null,
    )

    @Serializable
    public data class Records(
        public val home: String? = null,
        public val road: String? = null,
        public val conference: String? = null,
        public val division: String? = null,
    )

    @Serializable
    public data class NcaaConference(
        public val won: Int? = null,
        public val lost: Int? = null,
        public val points: Points? = null,
    )
}
