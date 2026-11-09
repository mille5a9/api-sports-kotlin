# api-sports-kotlin

[![CI](https://github.com/mille5a9/api-sports-kotlin/actions/workflows/ci.yml/badge.svg)](https://github.com/mille5a9/api-sports-kotlin/actions/workflows/ci.yml)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.mille5a9/api-nba-kotlin?label=Maven%20Central)](https://central.sonatype.com/namespace/io.github.mille5a9)
[![API docs](https://img.shields.io/badge/docs-dokka-7F52FF)](https://mille5a9.github.io/api-sports-kotlin/)
[![License](https://img.shields.io/badge/license-Apache%202.0-blue)](LICENSE)

Unofficial, coroutine-based Kotlin clients for the [API-SPORTS](https://api-sports.io) sports data APIs,
built on [Ktor](https://ktor.io) and [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization).

| Module | API | Coverage |
|---|---|---|
| [`api-nba-kotlin`](api-nba-kotlin) | [API-NBA v2](https://api-sports.io/documentation/nba/v2) | Every endpoint |
| [`api-nfl-kotlin`](api-nfl-kotlin) | [API-American-Football v1](https://api-sports.io/documentation/american-football/v1) (NFL & NCAA) | Every endpoint |
| [`api-sports-core`](api-sports-core) | — | Shared HTTP client, response envelope and errors |

## Install

Requires JDK 17+. Add the client for your sport; `api-sports-core` comes with it.

```kotlin
dependencies {
    implementation("io.github.mille5a9:api-nba-kotlin:2.0.0")
    implementation("io.github.mille5a9:api-nfl-kotlin:2.0.0")
}
```

You need an API key from [api-sports.io](https://api-sports.io) (free tier: 100 requests/day per API)
or from [RapidAPI](https://rapidapi.com/api-sports).

## Usage

```kotlin
import io.github.mille5a9.apisports.nba.ApiNbaClient
import io.github.mille5a9.apisports.nfl.ApiNflClient

suspend fun main() {
    val key = System.getenv("API_SPORTS_KEY")

    ApiNbaClient(key).use { nba ->
        val celtics = nba.getTeamByCode("BOS").response.single()
        val games = nba.getGamesByTeamAndSeason(celtics.id, season = 2024).response
        println("${celtics.name} played ${games.size} games in 2024-25")
    }

    ApiNflClient(key).use { nfl ->
        nfl.getStandings(league = ApiNflClient.NFL, season = 2024).response
            .filter { it.division == "AFC East" }
            .forEach { println("${it.position}. ${it.team.name} ${it.won}-${it.lost}") }
    }
}
```

Every method is a `suspend` function returning the API's response envelope,
[`ApiResponse<T>`](api-sports-core/src/main/kotlin/io/github/mille5a9/apisports/core/ApiResponse.kt):
`response` holds the data, and `results`, `parameters` and `paging` hold the metadata.

### Any combination of parameters

Each client has a convenience method for common queries, and a general method per endpoint that takes
a parameters object. You can build that object with named arguments or with a generated DSL:

```kotlin
nba.getGames(NbaParameters(team = 2, season = 2024))
nba.getGames { team = 2; season = 2024 }

nfl.getGames { league = ApiNflClient.NFL; season = 2024; timezone = "America/New_York" }
```

Unset parameters are left out of the request.

### Errors

API-SPORTS reports most failures (bad key, exhausted quota, invalid parameters) with an HTTP 200 and an
`errors` object. The client checks every response and throws `ApiSportsException` with the API's
messages. HTTP errors and responses that don't match the models also throw it:

```kotlin
try {
    nba.getAccountStatus()
} catch (e: ApiSportsException) {
    println(e.errors)      // {token=Error/Missing application key...}
    println(e.statusCode)  // 200
}
```

`getAccountStatus()` doesn't count against your daily quota, so you can use it to check a key.

### RapidAPI and Ktor configuration

```kotlin
ApiNflClient(
    apiKey = rapidApiKey,
    host = Host.RAPID_API,          // sends x-rapidapi-key / x-rapidapi-host instead of x-apisports-key
    engine = OkHttp.create(),       // any Ktor engine; defaults to CIO
) {
    install(HttpTimeout) { requestTimeoutMillis = 10_000 }
    install(Logging)
}
```

Clients own a Ktor `HttpClient` and implement `Closeable`, so use `use { }` or call `close()`.
For unit tests, pass a Ktor `MockEngine` as the `engine`.

## Sample app

[`samples`](samples) is a small command-line app built on the library:

```shell
export API_SPORTS_KEY=...
./gradlew :samples:run --args="nba standings 2024"
./gradlew :samples:run --args="nfl standings 2024"
./gradlew :samples:run --args="nfl game 13146"
```

## How it's built

- **One core, thin sport modules.** `api-sports-core` handles the base URL, authentication headers, HTTP
  caching, envelope decoding and error mapping. Each sport module only declares its endpoints, parameters
  and `@Serializable` models.
- **Models are tested against real API responses.** API-SPORTS sends many fields as `null` that its docs
  show as populated. Each module decodes full recorded responses
  (`src/test/resources/recorded`) in its tests. This found null advanced box-score stats in NBA games
  that a hand-written model would have missed.
- **No forgotten query parameters.** Parameter classes are `@Serializable` and turned into query strings
  by the serializer, so adding a property can't leave it out of requests.
- **Lenient where the API is inconsistent.** `parameters` and `errors` arrive as `{}` or `[]` depending on
  whether they're empty. Some fields are misspelled (`statisitcs`, `posession`), and NFL seasons come as
  strings in one endpoint and numbers in another. The models handle all of this, so you don't have to.
- **Library hygiene.** Kotlin explicit API mode, ktlint, Dokka API docs, Gradle convention plugins in
  `buildSrc`, and a version catalog.

## Building

```shell
./gradlew build                 # compile, ktlint and test every module
./gradlew ktlintFormat          # fix formatting
./gradlew :dokkaGenerate        # API docs for all modules -> build/dokka/html
./gradlew publishToMavenLocal   # install 2.0.0 into ~/.m2 to try it in another project
```

### Releasing

Publishing goes through the [Central Portal](https://central.sonatype.com) using
[vanniktech/gradle-maven-publish-plugin](https://github.com/vanniktech/gradle-maven-publish-plugin).
Set `VERSION_NAME` in `gradle.properties`, then publish a GitHub release tagged with that version.
[`publish.yml`](.github/workflows/publish.yml) builds, signs and releases all three artifacts. It needs the
repository secrets `MAVEN_CENTRAL_USERNAME`, `MAVEN_CENTRAL_PASSWORD`, `SIGNING_KEY` and `SIGNING_KEY_PASSWORD`.

## License

[Apache 2.0](LICENSE). This project is not affiliated with API-SPORTS.
