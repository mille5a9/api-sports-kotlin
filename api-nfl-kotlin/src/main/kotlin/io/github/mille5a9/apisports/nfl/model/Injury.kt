package io.github.mille5a9.apisports.nfl.model

import kotlinx.serialization.Serializable

/**
 * A current injury report entry, from `injuries`.
 *
 * @property date When the report was made, `YYYY-MM-DD`.
 * @property status e.g. `Questionable`, `Out`, `Injured Reserve`.
 */
@Serializable
public data class Injury(
    public val player: PlayerRef,
    public val team: TeamRef,
    public val date: String? = null,
    public val status: String? = null,
    public val description: String? = null,
)
