package io.github.mille5a9.apisports.nfl.model

import kotlinx.serialization.Serializable

/** A player's season statistics, from `players/statistics`, split by team and stat group. */
@Serializable
public data class PlayerStatistics(
    public val player: PlayerRef,
    public val teams: List<TeamStatistics> = emptyList(),
) {
    /** The player's statistics while playing for [team]. */
    @Serializable
    public data class TeamStatistics(
        public val team: TeamRef,
        public val groups: List<Group> = emptyList(),
    )

    /** A stat group such as `Passing`, `Rushing` or `Defense`. */
    @Serializable
    public data class Group(
        public val name: String,
        public val statistics: List<Statistic> = emptyList(),
    )
}
