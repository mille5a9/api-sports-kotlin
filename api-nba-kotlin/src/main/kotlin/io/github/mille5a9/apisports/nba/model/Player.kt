package io.github.mille5a9.apisports.nba.model

import kotlinx.serialization.Serializable

/**
 * Represents a response object for retrieving player information.
 *
 * @property id The unique identifier of the player.
 * @property firstname The first name of the player.
 * @property lastname The last name of the player.
 * @property birth The birth information of the player.
 * @property nba The NBA information of the player.
 * @property height The height information of the player.
 * @property weight The weight information of the player.
 * @property college The college attended by the player.
 * @property affiliation The affiliation of the player.
 * @property leagues The leagues in which the player has played.
 */
@Serializable
public data class Player(
    public val id: Int,
    public val firstname: String,
    public val lastname: String,
    public val birth: Birth,
    public val nba: Nba,
    public val height: Height,
    public val weight: Weight,
    public val college: String?,
    public val affiliation: String?,
    public val leagues: Leagues,
) {
    /**
     * Represents the birth information of a player.
     *
     * @property date The birth date of the player in the format "YYYY-mm-DD".
     * @property country The country of birth of the player.
     */
    @Serializable
    public data class Birth(
        public val date: String?,
        public val country: String?,
    )

    /**
     * Represents the NBA information of a player.
     *
     * @property start The starting season of the player in the NBA.
     * @property pro The number of seasons the player has played in the NBA.
     */
    @Serializable
    public data class Nba(
        public val start: Int?,
        public val pro: Int?,
    )

    /**
     * Represents the height information of a player.
     *
     * @property feets The height of the player in feet.
     * @property inches The height of the player in inches.
     * @property meters The height of the player in meters.
     */
    @Serializable
    public data class Height(
        public val feets: String?,
        public val inches: String?,
        public val meters: String?,
    )

    /**
     * Represents the weight information of a player.
     *
     * @property pounds The weight of the player in pounds.
     * @property kilograms The weight of the player in kilograms.
     */
    @Serializable
    public data class Weight(
        public val pounds: String?,
        public val kilograms: String?,
    )

    /**
     * Represents the leagues in which a player has played.
     *
     * @property standard The standard league in which the player has played.
     * @property africa The Africa league in which the player has played.
     * @property sacramento The Sacramento league in which the player has played.
     * @property vegas The Vegas league in which the player has played.
     * @property utah The Utah league in which the player has played.
     * @property orlando The Orlando league in which the player has played.
     */
    @Serializable
    public data class Leagues(
        public val standard: League? = null,
        public val africa: League? = null,
        public val sacramento: League? = null,
        public val vegas: League? = null,
        public val utah: League? = null,
        public val orlando: League? = null,
    )

    /**
     * Represents a league in which a player has played.
     *
     * @property jersey The jersey number of the player in the league.
     * @property active Indicates if the player is currently active in the league.
     * @property pos The position of the player in the league.
     */
    @Serializable
    public data class League(
        public val jersey: Int?,
        public val active: Boolean?,
        public val pos: String?,
    )
}
