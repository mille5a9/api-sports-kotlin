package io.github.mille5a9.apisports.nba.model

import kotlinx.serialization.Serializable

/**
 * Represents an NBA team.
 *
 * @property id The ID of the team.
 * @property name The name of the team.
 * @property nickname The nickname of the team.
 * @property code The code of the team.
 * @property logo The URL of the team's logo.
 */
@Serializable
public data class TeamSummary(
    public val id: Int,
    public val name: String,
    public val nickname: String?,
    public val code: String?,
    public val logo: String?,
)
