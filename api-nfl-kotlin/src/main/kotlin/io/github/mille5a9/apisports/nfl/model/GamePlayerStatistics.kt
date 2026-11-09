package io.github.mille5a9.apisports.nfl.model

import kotlinx.serialization.Serializable

/** One team's player box scores for a game, from `games/statistics/players`. */
@Serializable
public data class GamePlayerStatistics(
    public val team: TeamRef,
    public val groups: List<Group> = emptyList(),
) {
    /** A stat group such as `Passing`, `Rushing`, `Receiving`, `Defensive` or `Kicking`. */
    @Serializable
    public data class Group(
        public val name: String,
        public val players: List<PlayerLine> = emptyList(),
    )

    /** One player's line in a group, e.g. `statistics["yards"]`. */
    @Serializable
    public data class PlayerLine(
        public val player: PlayerRef,
        public val statistics: List<Statistic> = emptyList(),
    )
}
