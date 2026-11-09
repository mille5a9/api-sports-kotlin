# api-nba-kotlin

Kotlin client for [API-NBA v2](https://api-sports.io/documentation/nba/v2).

```kotlin
implementation("io.github.mille5a9:api-nba-kotlin:2.0.0")
```

```kotlin
ApiNbaClient(apiKey).use { nba ->
    val lebron = nba.searchPlayers("james").response.first { it.firstname == "LeBron" }
    val lines = nba.getPlayerStatisticsByPlayerAndSeason(lebron.id, 2024).response
    println("${lines.mapNotNull { it.points }.average()} PPG")
}
```

## Methods

Each general method takes [`NbaParameters`](src/main/kotlin/io/github/mille5a9/apisports/nba/NbaParameters.kt)
or a DSL block, e.g. `getGames { date = "2025-12-25" }`.

| Endpoint | General | Convenience |
|---|---|---|
| `status` | `getAccountStatus()` (free) | |
| `seasons` | `getSeasons()` | |
| `leagues` | `getLeagues()` | |
| `games` | `getGames(params)` | `getGameById`, `getGamesByDate`, `getGamesBySeason`, `getGamesByTeamAndSeason`, `getGamesBetweenTwoTeams`, `getLiveGames` |
| `games/statistics` | | `getGameStatistics(gameId)` |
| `teams` | `getTeams(params)` | `getAllTeams`, `getTeamById`, `getTeamByCode`, `getTeamsByConference`, `getTeamsByDivision`, `searchTeams` |
| `teams/statistics` | | `getTeamStatistics(teamId, season)` |
| `players` | `getPlayers(params)` | `getPlayerById`, `getPlayersByTeamAndSeason`, `getPlayersByCountry`, `searchPlayers` |
| `players/statistics` | `getPlayerStatistics(params)` | `getPlayersStatisticsByGame`, `getPlayersStatisticsByTeamAndSeason`, `getPlayerStatisticsByPlayerAndSeason` |
| `standings` | `getStandings(params)` | `getStandingsByConferenceAndSeason`, `getStandingsByDivisionAndSeason`, `getStandingsByTeamAndSeason` |

The convenience standings methods use the `standard` (NBA) league.
