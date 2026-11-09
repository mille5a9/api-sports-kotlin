package io.github.mille5a9.apisports.nfl

import com.faendir.kotlin.autodsl.AutoDsl
import io.github.mille5a9.apisports.core.QueryParameters
import io.github.mille5a9.apisports.core.encodeToQueryMap
import kotlinx.serialization.Serializable

/**
 * Query parameters accepted by the API-American-Football endpoints. Which combinations are valid depends
 * on the endpoint; see https://api-sports.io/documentation/american-football/v1. Unset (null) parameters
 * are not sent.
 *
 * Build one with named arguments, `NflParameters(league = ApiNflClient.NFL, season = 2024)`, or with the
 * generated DSL, `nflParameters { league = ApiNflClient.NFL; season = 2024 }`.
 *
 * @property id The id of a game, team, player, bookmaker or bet, depending on the endpoint.
 * @property league A league id: [ApiNflClient.NFL] or [ApiNflClient.NCAA].
 * @property season A season, e.g. `2024` for 2024-25.
 * @property team A team id.
 * @property player A player id (`injuries`).
 * @property game A game id (`odds`).
 * @property name An exact team or player name.
 * @property code A team code, e.g. `BUF`.
 * @property search At least 3 characters to search names.
 * @property date A date in `YYYY-MM-DD` format.
 * @property h2h Two team ids separated by a dash, e.g. `1-20`.
 * @property live `all` for games in progress.
 * @property timezone An IANA time zone from `timezone`, used for game dates.
 * @property current `true` to return only the current season (`leagues`).
 * @property group A stat group, e.g. `Passing` (`games/statistics/players`).
 * @property conference A conference name (`standings`).
 * @property division A division name (`standings`).
 * @property bookmaker A bookmaker id (`odds`).
 * @property bet A bet type id (`odds`).
 */
@AutoDsl
@Serializable
public data class NflParameters(
    public val id: Int? = null,
    public val league: Int? = null,
    public val season: Int? = null,
    public val team: Int? = null,
    public val player: Int? = null,
    public val game: Int? = null,
    public val name: String? = null,
    public val code: String? = null,
    public val search: String? = null,
    public val date: String? = null,
    public val h2h: String? = null,
    public val live: String? = null,
    public val timezone: String? = null,
    public val current: Boolean? = null,
    public val group: String? = null,
    public val conference: String? = null,
    public val division: String? = null,
    public val bookmaker: Int? = null,
    public val bet: Int? = null,
) : QueryParameters {
    override fun toQueryMap(): Map<String, String> = encodeToQueryMap()
}
