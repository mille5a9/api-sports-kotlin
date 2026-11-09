package io.github.mille5a9.apisports.nfl.model

import kotlinx.serialization.Serializable

/**
 * A scoring play, from `games/events`.
 *
 * @property quarter e.g. `First`, `Second`, `OT`.
 * @property minute Game clock when the play happened, e.g. `8:39`.
 * @property type e.g. `TD`, `FG`, `SF`, `PAT`.
 * @property score The score after the play.
 */
@Serializable
public data class GameEvent(
    public val quarter: String? = null,
    public val minute: String? = null,
    public val team: TeamRef,
    public val player: PlayerRef? = null,
    public val type: String? = null,
    public val comment: String? = null,
    public val score: HomeAway<Int>? = null,
)
