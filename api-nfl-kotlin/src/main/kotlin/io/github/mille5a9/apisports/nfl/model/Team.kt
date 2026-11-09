package io.github.mille5a9.apisports.nfl.model

import kotlinx.serialization.Serializable

/**
 * A team, from `teams`. Conference placeholders such as "AFC" and "NFC" are returned as teams
 * with most details null.
 */
@Serializable
public data class Team(
    public val id: Int,
    public val name: String,
    public val code: String? = null,
    public val city: String? = null,
    public val coach: String? = null,
    public val owner: String? = null,
    public val stadium: String? = null,
    public val established: Int? = null,
    public val logo: String? = null,
    public val country: Country? = null,
)
