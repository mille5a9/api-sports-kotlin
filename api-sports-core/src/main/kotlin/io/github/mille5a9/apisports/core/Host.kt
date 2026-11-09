package io.github.mille5a9.apisports.core

/**
 * Where requests are sent. API-SPORTS sells the same APIs directly and through RapidAPI;
 * each needs a key issued by that provider and uses different authentication headers.
 */
public enum class Host {
    /** https://api-sports.io — authenticates with the `x-apisports-key` header. */
    API_SPORTS,

    /** https://rapidapi.com/api-sports — authenticates with `x-rapidapi-key` and `x-rapidapi-host`. */
    RAPID_API,
}

/**
 * One API-SPORTS product, e.g. NBA or American Football.
 *
 * @property apiSportsUrl Base URL when calling API-SPORTS directly. Must end with `/`.
 * @property rapidApiHost Host name used for RapidAPI, also sent as the `x-rapidapi-host` header.
 */
public data class SportApi(
    public val apiSportsUrl: String,
    public val rapidApiHost: String,
) {
    /** The base URL for [host], always ending with `/`. */
    public fun baseUrl(host: Host): String =
        when (host) {
            Host.API_SPORTS -> apiSportsUrl
            Host.RAPID_API -> "https://$rapidApiHost/"
        }

    public companion object {
        /** API-NBA v2. */
        public val NBA: SportApi = SportApi(
            apiSportsUrl = "https://v2.nba.api-sports.io/",
            rapidApiHost = "api-nba-v1.p.rapidapi.com",
        )

        /** API-American-Football v1 (NFL and NCAA). */
        public val AMERICAN_FOOTBALL: SportApi = SportApi(
            apiSportsUrl = "https://v1.american-football.api-sports.io/",
            rapidApiHost = "api-american-football.p.rapidapi.com",
        )
    }
}
