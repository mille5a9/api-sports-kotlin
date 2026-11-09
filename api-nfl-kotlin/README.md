# api-nfl-kotlin

Kotlin client for [API-American-Football v1](https://api-sports.io/documentation/american-football/v1),
which covers the NFL (`ApiNflClient.NFL`, league 1) and NCAA football (`ApiNflClient.NCAA`, league 2).

```kotlin
implementation("io.github.mille5a9:api-nfl-kotlin:2.0.0")
```

```kotlin
ApiNflClient(apiKey).use { nfl ->
    val game = nfl.getGamesByDate("2025-02-09").response.single()   // Super Bowl LIX
    val passing = nfl.getGamePlayerStatistics(game.game.id, group = "Passing").response
    passing.flatMap { it.groups }.flatMap { it.players }.forEach { line ->
        println("${line.player.name}: ${line.statistics["comp att"]}, ${line.statistics["yards"]} yds")
    }
}
```

Player and season statistics come back from the API as name/value lists. Look them up with
`statistics["yards"]`; values are display strings such as `"3,522"` or `"11/15"`.

## Methods

| Endpoint | Methods |
|---|---|
| `status` | `getAccountStatus()` (free) |
| `timezone` | `getTimezones()` |
| `seasons` | `getSeasons()` |
| `leagues` | `getLeagues(params)` |
| `teams` | `getTeams(params)`, `getTeams(league, season)`, `getTeamById` |
| `players` | `getPlayers(params)`, `getPlayerById`, `getPlayersByTeamAndSeason`, `searchPlayers` |
| `players/statistics` | `getPlayerStatistics(playerId, season)`, `getPlayerStatisticsByTeam(teamId, season)` |
| `injuries` | `getInjuriesByTeam`, `getInjuriesByPlayer` |
| `games` | `getGames(params)`, `getGameById`, `getGamesBySeason`, `getGamesByTeamAndSeason`, `getGamesByDate`, `getGamesBetweenTwoTeams`, `getLiveGames` |
| `games/events` | `getGameEvents(gameId)` (scoring plays) |
| `games/statistics/teams` | `getGameTeamStatistics(gameId, teamId?)` |
| `games/statistics/players` | `getGamePlayerStatistics(gameId, teamId?, group?)` |
| `standings` | `getStandings(league, season, teamId?, conference?, division?)` |
| `standings/conferences` | `getConferences(league, season)` |
| `standings/divisions` | `getDivisions(league, season)` |
| `odds` | `getOdds(gameId, bookmakerId?, betId?)` |
| `odds/bets` | `getBetTypes(params)` |
| `odds/bookmakers` | `getBookmakers(params)` |

General methods take [`NflParameters`](src/main/kotlin/io/github/mille5a9/apisports/nfl/NflParameters.kt)
or a DSL block, e.g. `getGames { team = 20; season = 2024 }`.

> The `odds` model follows the documented shape but hasn't been checked against a non-empty recorded
> response yet, because odds are only kept for a few days around each game.
