package io.github.mille5a9.apisports.nfl.model

import kotlinx.serialization.Serializable

/** A bookmaker, from `odds/bookmakers`. */
@Serializable
public data class Bookmaker(
    public val id: Int,
    public val name: String? = null,
)

/** A bet type such as `Home/Away` or `Over/Under`, from `odds/bets`. */
@Serializable
public data class BetType(
    public val id: Int,
    public val name: String? = null,
)

/**
 * Pre-game odds for one game, from `odds`. Odds are only kept from 1-7 days before a game
 * until shortly after it, so this endpoint is often empty for past games.
 *
 * @property update When the odds were last refreshed, as an ISO-8601 timestamp.
 */
@Serializable
public data class GameOdds(
    public val game: GameId,
    public val league: League? = null,
    public val country: Country? = null,
    public val update: String? = null,
    public val bookmakers: List<BookmakerOdds> = emptyList(),
) {
    @Serializable
    public data class GameId(
        public val id: Int,
    )

    @Serializable
    public data class League(
        public val id: Int,
        public val name: String? = null,
        public val season: Int? = null,
        public val logo: String? = null,
    )

    @Serializable
    public data class BookmakerOdds(
        public val id: Int,
        public val name: String? = null,
        public val bets: List<Bet> = emptyList(),
    )

    @Serializable
    public data class Bet(
        public val id: Int,
        public val name: String? = null,
        public val values: List<Value> = emptyList(),
    )

    /** One outcome and its decimal odd, e.g. `value = "Home"`, `odd = "1.85"`. */
    @Serializable
    public data class Value(
        public val value: String? = null,
        public val odd: String? = null,
    )
}
