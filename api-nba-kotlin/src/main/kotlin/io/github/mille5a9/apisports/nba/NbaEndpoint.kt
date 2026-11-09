package io.github.mille5a9.apisports.nba

import io.github.mille5a9.apisports.core.Endpoint

/** The endpoints of API-NBA v2. */
public enum class NbaEndpoint(
    override val path: String,
) : Endpoint {
    LEAGUES("leagues"),
    SEASONS("seasons"),
    TEAMS("teams"),
    TEAM_STATISTICS("teams/statistics"),
    PLAYERS("players"),
    PLAYER_STATISTICS("players/statistics"),
    GAMES("games"),
    GAME_STATISTICS("games/statistics"),
    STANDINGS("standings"),
}
