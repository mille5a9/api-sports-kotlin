package io.github.mille5a9.apisports.nfl.model

import kotlinx.serialization.Serializable

/**
 * A player, from `players`.
 *
 * @property height Display string, e.g. `6' 2"`.
 * @property weight Display string, e.g. `225 lbs`.
 * @property group The unit the player belongs to, e.g. `Offense`, `Defense`, `Special Teams`.
 * @property position Abbreviated position, e.g. `QB`.
 * @property experience Years in the league.
 */
@Serializable
public data class Player(
    public val id: Int,
    public val name: String,
    public val age: Int? = null,
    public val height: String? = null,
    public val weight: String? = null,
    public val college: String? = null,
    public val group: String? = null,
    public val position: String? = null,
    public val number: Int? = null,
    public val salary: String? = null,
    public val experience: Int? = null,
    public val image: String? = null,
)
