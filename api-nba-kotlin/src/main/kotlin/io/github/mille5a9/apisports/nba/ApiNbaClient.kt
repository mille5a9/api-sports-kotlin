package io.github.mille5a9.apisports.nba

import io.github.mille5a9.apisports.core.ApiResponse
import io.github.mille5a9.apisports.core.ApiSportsClient
import io.github.mille5a9.apisports.core.ApiSportsException
import io.github.mille5a9.apisports.core.Host
import io.github.mille5a9.apisports.core.SportApi
import io.github.mille5a9.apisports.nba.model.Game
import io.github.mille5a9.apisports.nba.model.GameTeamStatistics
import io.github.mille5a9.apisports.nba.model.Player
import io.github.mille5a9.apisports.nba.model.PlayerStatistics
import io.github.mille5a9.apisports.nba.model.Standing
import io.github.mille5a9.apisports.nba.model.Team
import io.github.mille5a9.apisports.nba.model.TeamStatistics
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine

/**
 * Client for API-NBA v2 (https://api-sports.io/documentation/nba/v2).
 *
 * ```kotlin
 * ApiNbaClient(apiKey = System.getenv("API_SPORTS_KEY")).use { nba ->
 *     val celtics = nba.getTeamByCode("BOS").response.single()
 *     val games = nba.getGames { team = celtics.id; season = 2024 }.response
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
public class ApiNbaClient(
    apiKey: String,
    host: Host = Host.API_SPORTS,
    engine: HttpClientEngine? = null,
    configure: HttpClientConfig<*>.() -> Unit = {},
) : ApiSportsClient(SportApi.NBA, apiKey, host, engine, configure) {
    // region Endpoints with arbitrary parameters

    /** `games` with any combination of [NbaParameters]. */
    public suspend fun getGames(params: NbaParameters): ApiResponse<List<Game>> = get(NbaEndpoint.GAMES, params)

    /** `games` built with the parameter DSL, e.g. `getGames { team = 2; season = 2024 }`. */
    public suspend fun getGames(block: NbaParametersBuilder.() -> Unit): ApiResponse<List<Game>> =
        getGames(nbaParameters(block))

    /** `teams` with any combination of [NbaParameters]. */
    public suspend fun getTeams(params: NbaParameters): ApiResponse<List<Team>> = get(NbaEndpoint.TEAMS, params)

    /** `teams` built with the parameter DSL. */
    public suspend fun getTeams(block: NbaParametersBuilder.() -> Unit): ApiResponse<List<Team>> =
        getTeams(nbaParameters(block))

    /** `players` with any combination of [NbaParameters]. */
    public suspend fun getPlayers(params: NbaParameters): ApiResponse<List<Player>> =
        get(NbaEndpoint.PLAYERS, params)

    /** `players` built with the parameter DSL. */
    public suspend fun getPlayers(block: NbaParametersBuilder.() -> Unit): ApiResponse<List<Player>> =
        getPlayers(nbaParameters(block))

    /** `standings` with any combination of [NbaParameters]. `league` and `season` are required by the API. */
    public suspend fun getStandings(params: NbaParameters): ApiResponse<List<Standing>> =
        get(NbaEndpoint.STANDINGS, params)

    /** `standings` built with the parameter DSL. */
    public suspend fun getStandings(block: NbaParametersBuilder.() -> Unit): ApiResponse<List<Standing>> =
        getStandings(nbaParameters(block))

    /** `players/statistics` with any combination of [NbaParameters]. */
    public suspend fun getPlayerStatistics(params: NbaParameters): ApiResponse<List<PlayerStatistics>> =
        get(NbaEndpoint.PLAYER_STATISTICS, params)

    /** `players/statistics` built with the parameter DSL. */
    public suspend fun getPlayerStatistics(
        block: NbaParametersBuilder.() -> Unit,
    ): ApiResponse<List<PlayerStatistics>> = getPlayerStatistics(nbaParameters(block))

    // endregion

    // region Seasons and leagues

    /** All seasons available, e.g. `2015..2024`. */
    public suspend fun getSeasons(): ApiResponse<List<Int>> = get(NbaEndpoint.SEASONS)

    /** All leagues available, e.g. `standard`, `vegas`. */
    public suspend fun getLeagues(): ApiResponse<List<String>> = get(NbaEndpoint.LEAGUES)

    // endregion

    // region Statistics

    /** Both teams' box-score totals for a game. */
    public suspend fun getGameStatistics(gameId: Int): ApiResponse<List<GameTeamStatistics>> =
        get(NbaEndpoint.GAME_STATISTICS, NbaParameters(id = gameId))

    /** A team's season totals. */
    public suspend fun getTeamStatistics(
        teamId: Int,
        season: Int,
    ): ApiResponse<List<TeamStatistics>> =
        get(NbaEndpoint.TEAM_STATISTICS, NbaParameters(id = teamId, season = season))

    /** Every box-score line for a team's players in a season. */
    public suspend fun getPlayersStatisticsByTeamAndSeason(
        teamId: Int,
        season: Int,
    ): ApiResponse<List<PlayerStatistics>> = getPlayerStatistics(NbaParameters(team = teamId, season = season))

    /** Every box-score line for one player in a season. */
    public suspend fun getPlayerStatisticsByPlayerAndSeason(
        playerId: Int,
        season: Int,
    ): ApiResponse<List<PlayerStatistics>> = getPlayerStatistics(NbaParameters(id = playerId, season = season))

    /** Every player's box-score line for a game. */
    public suspend fun getPlayersStatisticsByGame(gameId: Int): ApiResponse<List<PlayerStatistics>> =
        getPlayerStatistics(NbaParameters(game = gameId))

    // endregion

    // region Games

    /** A single game. */
    public suspend fun getGameById(gameId: Int): ApiResponse<List<Game>> = getGames(NbaParameters(id = gameId))

    /** All head-to-head games between two teams. */
    public suspend fun getGamesBetweenTwoTeams(
        teamId1: Int,
        teamId2: Int,
    ): ApiResponse<List<Game>> = getGames(NbaParameters(h2h = "$teamId1-$teamId2"))

    /** Games currently in progress. */
    public suspend fun getLiveGames(): ApiResponse<List<Game>> = getGames(NbaParameters(live = "all"))

    /** Every game in a season. This is a large response. */
    public suspend fun getGamesBySeason(season: Int): ApiResponse<List<Game>> =
        getGames(NbaParameters(season = season))

    /** Games on a date, formatted `YYYY-MM-DD`. */
    public suspend fun getGamesByDate(date: String): ApiResponse<List<Game>> = getGames(NbaParameters(date = date))

    /** A team's games in a season. */
    public suspend fun getGamesByTeamAndSeason(
        teamId: Int,
        season: Int,
    ): ApiResponse<List<Game>> = getGames(NbaParameters(team = teamId, season = season))

    // endregion

    // region Teams

    /** Every team, including All-Star and international teams. */
    public suspend fun getAllTeams(): ApiResponse<List<Team>> = get(NbaEndpoint.TEAMS)

    /** A single team. */
    public suspend fun getTeamById(teamId: Int): ApiResponse<List<Team>> = getTeams(NbaParameters(id = teamId))

    /** Teams in a conference: `east` or `west`. */
    public suspend fun getTeamsByConference(conference: String): ApiResponse<List<Team>> =
        getTeams(NbaParameters(conference = conference))

    /** Teams in a division, e.g. `atlantic`. */
    public suspend fun getTeamsByDivision(division: String): ApiResponse<List<Team>> =
        getTeams(NbaParameters(division = division))

    /** A team by its three-letter code, e.g. `BOS`. */
    public suspend fun getTeamByCode(code: String): ApiResponse<List<Team>> = getTeams(NbaParameters(code = code))

    /** Teams whose name contains [query] (at least 3 characters). */
    public suspend fun searchTeams(query: String): ApiResponse<List<Team>> = getTeams(NbaParameters(search = query))

    // endregion

    // region Players

    /** A team's roster for a season. */
    public suspend fun getPlayersByTeamAndSeason(
        teamId: Int,
        season: Int,
    ): ApiResponse<List<Player>> = getPlayers(NbaParameters(team = teamId, season = season))

    /** A single player. */
    public suspend fun getPlayerById(playerId: Int): ApiResponse<List<Player>> =
        getPlayers(NbaParameters(id = playerId))

    /** Players born in a country. */
    public suspend fun getPlayersByCountry(country: String): ApiResponse<List<Player>> =
        getPlayers(NbaParameters(country = country))

    /** Players whose name contains [query] (at least 3 characters). */
    public suspend fun searchPlayers(query: String): ApiResponse<List<Player>> =
        getPlayers(NbaParameters(search = query))

    // endregion

    // region Standings

    /** Standings for a conference (`east` / `west`) in the `standard` league. */
    public suspend fun getStandingsByConferenceAndSeason(
        conference: String,
        season: Int,
    ): ApiResponse<List<Standing>> =
        getStandings(NbaParameters(league = STANDARD_LEAGUE, conference = conference, season = season))

    /** Standings for a division in the `standard` league. */
    public suspend fun getStandingsByDivisionAndSeason(
        division: String,
        season: Int,
    ): ApiResponse<List<Standing>> =
        getStandings(NbaParameters(league = STANDARD_LEAGUE, division = division, season = season))

    /** A team's standing in the `standard` league. */
    public suspend fun getStandingsByTeamAndSeason(
        teamId: Int,
        season: Int,
    ): ApiResponse<List<Standing>> =
        getStandings(NbaParameters(league = STANDARD_LEAGUE, team = teamId, season = season))

    // endregion

    private companion object {
        const val STANDARD_LEAGUE = "standard"
    }
}
