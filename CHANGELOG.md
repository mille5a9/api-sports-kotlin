# Changelog

## 2.0.0

The first release of the multi-module project. It adds `api-nfl-kotlin` and `api-sports-core`, and
replaces `api-nba-kotlin` 1.0.x-alpha.

### Added
- `api-nfl-kotlin`: every endpoint of API-American-Football v1 (NFL and NCAA).
- `api-sports-core`, a published artifact holding the shared client.
- `ApiSportsException` for API-reported errors, HTTP errors and decoding failures.
- Public general methods for every endpoint (`getGames(params)`, `getTeams(params)`, ...) with DSL overloads.
- `Closeable` clients, and a `configure` block for custom Ktor plugins.
- `Host.RAPID_API` now also sends the `x-rapidapi-host` header that RapidAPI requires.
  `Host.API_SPORTS` sends `x-apisports-key`.

### Fixed
- `getTeamByCode()` didn't send `code`, so it returned every team.
- An error response (`"errors": {...}`) crashed decoding instead of reporting the error.
- NBA fields that recorded API responses show as `null` are now nullable, so they no longer fail to
  decode or hold `null` in non-null types. These are the advanced team box-score stats (`fastBreakPoints`
  and others) and player-line `pos`. Player-line stats, team codes and nicknames are also nullable as a
  precaution for players who didn't play and for non-NBA teams.

### Changed (breaking)
- Packages moved from `api.nba.kotlin` and the root package to `io.github.mille5a9.apisports.{core,nba,nfl}`.
- Model names describe a single item: `GamesResponse` → `Game`, `TeamsResponse` → `Team`,
  `PlayersResponse` → `Player`, `StandingsResponse` → `Standing`, `PlayersStatisticsResponse` →
  `PlayerStatistics`, `TeamsStatisticsResponse` → `TeamStatistics`, `GamesStatisticsResponse` →
  `GameTeamStatistics`. The short team model is now `TeamSummary`.
- `EndpointResponse<T>` → `ApiResponse<List<T>>`, and `errors` is now a `Map<String, String>`.
  `StatusResponse` → `ApiResponse<AccountStatus>`.
- Constructor: `ApiNbaClient(host, key)` → `ApiNbaClient(apiKey, host = Host.API_SPORTS)`.
  `HostsEnum` → `Host`.
- Gson replaced by kotlinx.serialization. Ktor 2 → 3. Requires JDK 17.
