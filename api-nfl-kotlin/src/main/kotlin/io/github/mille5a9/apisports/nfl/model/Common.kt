package io.github.mille5a9.apisports.nfl.model

import kotlinx.serialization.Serializable

/** A country as embedded in leagues and teams. */
@Serializable
public data class Country(
    public val name: String?,
    public val code: String? = null,
    public val flag: String? = null,
)

/** The short team reference embedded in games, standings, injuries and statistics. */
@Serializable
public data class TeamRef(
    public val id: Int,
    public val name: String?,
    public val logo: String? = null,
)

/** The short player reference embedded in events, injuries and statistics. */
@Serializable
public data class PlayerRef(
    public val id: Int,
    public val name: String?,
    public val image: String? = null,
)

/** A value for each side of a game. */
@Serializable
public data class HomeAway<T>(
    public val home: T,
    public val away: T,
)

/**
 * One named statistic. API-SPORTS returns values as display strings, e.g. `"3,522"`, `"11/15"` or `"60.8"`,
 * and sends `null` for stats it has no value for.
 */
@Serializable
public data class Statistic(
    public val name: String,
    public val value: String?,
)

/** Looks up a statistic by its API name, e.g. `stats["passing touchdowns"]`. */
public operator fun List<Statistic>.get(name: String): String? = firstOrNull { it.name == name }?.value
