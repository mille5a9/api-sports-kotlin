package io.github.mille5a9.apisports.nba

import com.faendir.kotlin.autodsl.AutoDsl
import io.github.mille5a9.apisports.core.QueryParameters
import io.github.mille5a9.apisports.core.encodeToQueryMap
import kotlinx.serialization.Serializable

/**
 * Query parameters accepted by the API-NBA endpoints. Which combinations are valid depends on the
 * endpoint; see https://api-sports.io/documentation/nba/v2. Unset (null) parameters are not sent.
 *
 * Build one with named arguments, `NbaParameters(team = 1, season = 2023)`, or with the generated
 * DSL, `nbaParameters { team = 1; season = 2023 }`.
 *
 * @property id The id of a game, team or player, depending on the endpoint.
 * @property game A game id (`players/statistics`).
 * @property season A season, e.g. `2023` for 2023-24.
 * @property team A team id.
 * @property league A league, e.g. `standard`, `vegas`, `africa`.
 * @property conference `east` or `west`.
 * @property division e.g. `atlantic`, `pacific`.
 * @property code A three-letter team code, e.g. `BOS`.
 * @property h2h Two team ids separated by a dash, e.g. `1-4`.
 * @property date A date in `YYYY-MM-DD` format.
 * @property live `all` for games in progress.
 * @property country A country name (`players`).
 * @property name A team or player name to match exactly.
 * @property search At least 3 characters to search team or player names.
 */
@AutoDsl
@Serializable
public data class NbaParameters(
    public val id: Int? = null,
    public val game: Int? = null,
    public val season: Int? = null,
    public val team: Int? = null,
    public val league: String? = null,
    public val conference: String? = null,
    public val division: String? = null,
    public val code: String? = null,
    public val h2h: String? = null,
    public val date: String? = null,
    public val live: String? = null,
    public val country: String? = null,
    public val name: String? = null,
    public val search: String? = null,
) : QueryParameters {
    override fun toQueryMap(): Map<String, String> = encodeToQueryMap()
}
