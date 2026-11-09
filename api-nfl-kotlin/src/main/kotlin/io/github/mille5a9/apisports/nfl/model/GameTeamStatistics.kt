package io.github.mille5a9.apisports.nfl.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * One team's box score for a game, from `games/statistics/teams`.
 * Ratios such as [FirstDowns.thirdDownEfficiency] are display strings like `"5-12"`.
 */
@Serializable
public data class GameTeamStatistics(
    public val team: TeamRef,
    public val statistics: Statistics,
) {
    @Serializable
    public data class Statistics(
        @SerialName("first_downs") public val firstDowns: FirstDowns? = null,
        public val plays: Total? = null,
        public val yards: Yards? = null,
        public val passing: Passing? = null,
        // Misspelled by the API.
        @SerialName("rushings") public val rushing: Rushing? = null,
        @SerialName("red_zone") public val redZone: RedZone? = null,
        public val penalties: TotalText? = null,
        public val turnovers: Turnovers? = null,
        // Misspelled by the API.
        @SerialName("posession") public val possession: TotalText? = null,
        public val interceptions: Total? = null,
        @SerialName("fumbles_recovered") public val fumblesRecovered: Total? = null,
        public val sacks: Total? = null,
        public val safeties: Total? = null,
        @SerialName("int_touchdowns") public val interceptionTouchdowns: Total? = null,
        @SerialName("points_against") public val pointsAgainst: Total? = null,
    )

    @Serializable
    public data class Total(
        public val total: Int? = null,
    )

    /** A total that the API formats as text, e.g. penalties `"7-55"` or possession `"31:12"`. */
    @Serializable
    public data class TotalText(
        public val total: String? = null,
    )

    @Serializable
    public data class FirstDowns(
        public val total: Int? = null,
        public val passing: Int? = null,
        public val rushing: Int? = null,
        @SerialName("from_penalties") public val fromPenalties: Int? = null,
        @SerialName("third_down_efficiency") public val thirdDownEfficiency: String? = null,
        @SerialName("fourth_down_efficiency") public val fourthDownEfficiency: String? = null,
    )

    @Serializable
    public data class Yards(
        public val total: Int? = null,
        @SerialName("yards_per_play") public val yardsPerPlay: String? = null,
        @SerialName("total_drives") public val totalDrives: String? = null,
    )

    @Serializable
    public data class Passing(
        public val total: Int? = null,
        @SerialName("comp_att") public val completionsAttempts: String? = null,
        @SerialName("yards_per_pass") public val yardsPerPass: String? = null,
        @SerialName("interceptions_thrown") public val interceptionsThrown: Int? = null,
        @SerialName("sacks_yards_lost") public val sacksYardsLost: String? = null,
    )

    @Serializable
    public data class Rushing(
        public val total: Int? = null,
        public val attempts: Int? = null,
        @SerialName("yards_per_rush") public val yardsPerRush: String? = null,
    )

    @Serializable
    public data class RedZone(
        @SerialName("made_att") public val madeAttempts: String? = null,
    )

    @Serializable
    public data class Turnovers(
        public val total: Int? = null,
        @SerialName("lost_fumbles") public val lostFumbles: Int? = null,
        public val interceptions: Int? = null,
    )
}
