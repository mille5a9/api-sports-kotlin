package io.github.mille5a9.apisports.nfl

import io.github.mille5a9.apisports.core.ApiResponse
import io.github.mille5a9.apisports.core.ApiSportsClient
import io.github.mille5a9.apisports.core.ApiSportsException
import io.github.mille5a9.apisports.core.Host
import io.github.mille5a9.apisports.core.SportApi
import io.github.mille5a9.apisports.nfl.model.BetType
import io.github.mille5a9.apisports.nfl.model.Bookmaker
import io.github.mille5a9.apisports.nfl.model.Game
import io.github.mille5a9.apisports.nfl.model.GameEvent
import io.github.mille5a9.apisports.nfl.model.GameOdds
import io.github.mille5a9.apisports.nfl.model.GamePlayerStatistics
import io.github.mille5a9.apisports.nfl.model.GameTeamStatistics
import io.github.mille5a9.apisports.nfl.model.Injury
import io.github.mille5a9.apisports.nfl.model.LeagueInfo
import io.github.mille5a9.apisports.nfl.model.Player
import io.github.mille5a9.apisports.nfl.model.PlayerStatistics
import io.github.mille5a9.apisports.nfl.model.Standing
import io.github.mille5a9.apisports.nfl.model.Team
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine

/**
 * Client for API-American-Football v1 (https://api-sports.io/documentation/american-football/v1),
 * which covers the NFL ([NFL]) and NCAA football ([NCAA]).
 *
 * ```kotlin
 * ApiNflClient(apiKey = System.getenv("API_SPORTS_KEY")).use { nfl ->
 *     val standings = nfl.getStandings(league = ApiNflClient.NFL, season = 2024).response
 *     val bills = standings.first { it.team.name == "Buffalo Bills" }
 *     println("${bills.won}-${bills.lost} (${bills.division})")
 * }
 * ```
 *
 * Every call is a suspending function returning the API's [ApiResponse] envelope, and throws
 * [ApiSportsException] if the request fails or the API reports an error.
 *
 * @param apiKey Your key for [host].
 * @param host Whether to call API-SPORTS directly or through RapidAPI.
 * @param engine The Ktor engine to use. Defaults to CIO.
 * @param configure Extra Ktor client configuration.
 */
public class ApiNflClient(
    apiKey: String,
    host: Host = Host.API_SPORTS,
    engine: HttpClientEngine? = null,
    configure: HttpClientConfig<*>.() -> Unit = {},
) : ApiSportsClient(SportApi.AMERICAN_FOOTBALL, apiKey, host, engine, configure) {
    // region Reference data

    /** Time zones accepted by the `timezone` parameter of [getGames]. */
    public suspend fun getTimezones(): ApiResponse<List<String>> = get(NflEndpoint.TIMEZONE)

    /** All seasons available, e.g. `2010..2025`. */
    public suspend fun getSeasons(): ApiResponse<List<Int>> = get(NflEndpoint.SEASONS)

    /** Leagues with their seasons and per-season data coverage. Accepts `id`, `season` and `current`. */
    public suspend fun getLeagues(params: NflParameters = NflParameters()): ApiResponse<List<LeagueInfo>> =
        get(NflEndpoint.LEAGUES, params)

    // endregion

    // region Teams and players

    /** `teams` with any combination of `id`, `league`, `season`, `name`, `code` and `search`. */
    public suspend fun getTeams(params: NflParameters): ApiResponse<List<Team>> = get(NflEndpoint.TEAMS, params)

    /** `teams` built with the parameter DSL. */
    public suspend fun getTeams(block: NflParametersBuilder.() -> Unit): ApiResponse<List<Team>> =
        getTeams(nflParameters(block))

    /** Every team in a league and season. */
    public suspend fun getTeams(
        league: Int,
        season: Int,
    ): ApiResponse<List<Team>> = getTeams(NflParameters(league = league, season = season))

    /** A single team. */
    public suspend fun getTeamById(teamId: Int): ApiResponse<List<Team>> = getTeams(NflParameters(id = teamId))

    /** `players` with any combination of `id`, `name`, `team`, `season` and `search`. */
    public suspend fun getPlayers(params: NflParameters): ApiResponse<List<Player>> =
        get(NflEndpoint.PLAYERS, params)

    /** `players` built with the parameter DSL. */
    public suspend fun getPlayers(block: NflParametersBuilder.() -> Unit): ApiResponse<List<Player>> =
        getPlayers(nflParameters(block))

    /** A single player. */
    public suspend fun getPlayerById(playerId: Int): ApiResponse<List<Player>> =
        getPlayers(NflParameters(id = playerId))

    /** A team's roster for a season. */
    public suspend fun getPlayersByTeamAndSeason(
        teamId: Int,
        season: Int,
    ): ApiResponse<List<Player>> = getPlayers(NflParameters(team = teamId, season = season))

    /** Players whose name contains [query] (at least 3 characters). */
    public suspend fun searchPlayers(query: String): ApiResponse<List<Player>> =
        getPlayers(NflParameters(search = query))

    /** A player's season statistics. */
    public suspend fun getPlayerStatistics(
        playerId: Int,
        season: Int,
    ): ApiResponse<List<PlayerStatistics>> =
        get(NflEndpoint.PLAYER_STATISTICS, NflParameters(id = playerId, season = season))

    /** Season statistics for every player on a team. */
    public suspend fun getPlayerStatisticsByTeam(
        teamId: Int,
        season: Int,
    ): ApiResponse<List<PlayerStatistics>> =
        get(NflEndpoint.PLAYER_STATISTICS, NflParameters(team = teamId, season = season))

    /** The current injury report for a team. */
    public suspend fun getInjuriesByTeam(teamId: Int): ApiResponse<List<Injury>> =
        get(NflEndpoint.INJURIES, NflParameters(team = teamId))

    /** The current injury report for a player. */
    public suspend fun getInjuriesByPlayer(playerId: Int): ApiResponse<List<Injury>> =
        get(NflEndpoint.INJURIES, NflParameters(player = playerId))

    // endregion

    // region Games

    /** `games` with any combination of `id`, `date`, `league`, `season`, `team`, `h2h`, `live` and `timezone`. */
    public suspend fun getGames(params: NflParameters): ApiResponse<List<Game>> = get(NflEndpoint.GAMES, params)

    /** `games` built with the parameter DSL, e.g. `getGames { league = NFL; season = 2024; team = 20 }`. */
    public suspend fun getGames(block: NflParametersBuilder.() -> Unit): ApiResponse<List<Game>> =
        getGames(nflParameters(block))

    /** A single game. */
    public suspend fun getGameById(gameId: Int): ApiResponse<List<Game>> = getGames(NflParameters(id = gameId))

    /** Every game in a league and season, including pre- and post-season. */
    public suspend fun getGamesBySeason(
        league: Int,
        season: Int,
    ): ApiResponse<List<Game>> = getGames(NflParameters(league = league, season = season))

    /** A team's games in a season. */
    public suspend fun getGamesByTeamAndSeason(
        teamId: Int,
        season: Int,
    ): ApiResponse<List<Game>> = getGames(NflParameters(team = teamId, season = season))

    /** Games on a date, formatted `YYYY-MM-DD`. */
    public suspend fun getGamesByDate(date: String): ApiResponse<List<Game>> = getGames(NflParameters(date = date))

    /** All head-to-head games between two teams. */
    public suspend fun getGamesBetweenTwoTeams(
        teamId1: Int,
        teamId2: Int,
    ): ApiResponse<List<Game>> = getGames(NflParameters(h2h = "$teamId1-$teamId2"))

    /** Games currently in progress. */
    public suspend fun getLiveGames(): ApiResponse<List<Game>> = getGames(NflParameters(live = "all"))

    /** Scoring plays for a game. */
    public suspend fun getGameEvents(gameId: Int): ApiResponse<List<GameEvent>> =
        get(NflEndpoint.GAME_EVENTS, NflParameters(id = gameId))

    /** Team box scores for a game; pass [teamId] for one side only. */
    public suspend fun getGameTeamStatistics(
        gameId: Int,
        teamId: Int? = null,
    ): ApiResponse<List<GameTeamStatistics>> =
        get(NflEndpoint.GAME_TEAM_STATISTICS, NflParameters(id = gameId, team = teamId))

    /** Player box scores for a game, optionally for one team and/or stat [group] (e.g. `Passing`). */
    public suspend fun getGamePlayerStatistics(
        gameId: Int,
        teamId: Int? = null,
        group: String? = null,
    ): ApiResponse<List<GamePlayerStatistics>> =
        get(NflEndpoint.GAME_PLAYER_STATISTICS, NflParameters(id = gameId, team = teamId, group = group))

    // endregion

    // region Standings

    /** Standings for a league and season, optionally narrowed by team, conference or division. */
    public suspend fun getStandings(
        league: Int,
        season: Int,
        teamId: Int? = null,
        conference: String? = null,
        division: String? = null,
    ): ApiResponse<List<Standing>> =
        get(
            NflEndpoint.STANDINGS,
            NflParameters(
                league = league,
                season = season,
                team = teamId,
                conference = conference,
                division = division,
            ),
        )

    /** Conference names for a league and season, for use with [getStandings]. */
    public suspend fun getConferences(
        league: Int,
        season: Int,
    ): ApiResponse<List<String>> =
        get(NflEndpoint.STANDINGS_CONFERENCES, NflParameters(league = league, season = season))

    /** Division names for a league and season, for use with [getStandings]. */
    public suspend fun getDivisions(
        league: Int,
        season: Int,
    ): ApiResponse<List<String>> =
        get(NflEndpoint.STANDINGS_DIVISIONS, NflParameters(league = league, season = season))

    // endregion

    // region Odds

    /** Pre-game odds for a game, optionally for one [bookmakerId] and/or [betId]. */
    public suspend fun getOdds(
        gameId: Int,
        bookmakerId: Int? = null,
        betId: Int? = null,
    ): ApiResponse<List<GameOdds>> =
        get(NflEndpoint.ODDS, NflParameters(game = gameId, bookmaker = bookmakerId, bet = betId))

    /** Bet types available in [getOdds]. Accepts `id` and `search`. */
    public suspend fun getBetTypes(params: NflParameters = NflParameters()): ApiResponse<List<BetType>> =
        get(NflEndpoint.ODDS_BETS, params)

    /** Bookmakers available in [getOdds]. Accepts `id` and `search`. */
    public suspend fun getBookmakers(params: NflParameters = NflParameters()): ApiResponse<List<Bookmaker>> =
        get(NflEndpoint.ODDS_BOOKMAKERS, params)

    // endregion

    public companion object {
        /** League id of the NFL. */
        public const val NFL: Int = 1

        /** League id of NCAA football. */
        public const val NCAA: Int = 2
    }
}
