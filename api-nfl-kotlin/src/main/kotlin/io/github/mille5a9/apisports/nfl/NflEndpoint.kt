package io.github.mille5a9.apisports.nfl

import io.github.mille5a9.apisports.core.Endpoint

/** The endpoints of API-American-Football v1. */
public enum class NflEndpoint(
    override val path: String,
) : Endpoint {
    TIMEZONE("timezone"),
    SEASONS("seasons"),
    LEAGUES("leagues"),
    TEAMS("teams"),
    PLAYERS("players"),
    PLAYER_STATISTICS("players/statistics"),
    INJURIES("injuries"),
    GAMES("games"),
    GAME_EVENTS("games/events"),
    GAME_TEAM_STATISTICS("games/statistics/teams"),
    GAME_PLAYER_STATISTICS("games/statistics/players"),
    STANDINGS("standings"),
    STANDINGS_CONFERENCES("standings/conferences"),
    STANDINGS_DIVISIONS("standings/divisions"),
    ODDS("odds"),
    ODDS_BETS("odds/bets"),
    ODDS_BOOKMAKERS("odds/bookmakers"),
}
