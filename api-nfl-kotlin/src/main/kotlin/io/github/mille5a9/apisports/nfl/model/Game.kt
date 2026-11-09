package io.github.mille5a9.apisports.nfl.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** A game, from `games`. */
@Serializable
public data class Game(
    public val game: Info,
    public val league: League,
    public val teams: HomeAway<TeamRef>,
    public val scores: HomeAway<Score>,
) {
    /**
     * @property stage e.g. `Pre Season`, `Regular Season`, `Post Season`.
     * @property week e.g. `Week 1`, `Wild Card`, `Hall of Fame Weekend`.
     */
    @Serializable
    public data class Info(
        public val id: Int,
        public val stage: String? = null,
        public val week: String? = null,
        public val date: Date,
        public val venue: Venue? = null,
        public val status: Status,
    )

    /**
     * Kickoff time. [date] and [time] are in [timezone], which is UTC unless the request set `timezone`.
     *
     * @property timestamp Kickoff as Unix epoch seconds.
     */
    @Serializable
    public data class Date(
        public val timezone: String,
        public val date: String,
        public val time: String? = null,
        public val timestamp: Long,
    )

    @Serializable
    public data class Venue(
        public val name: String? = null,
        public val city: String? = null,
    )

    /**
     * @property short Status code: `NS` (not started), `Q1`-`Q4`, `OT`, `HT`, `FT`, `AOT`, `CANC`, `PST`.
     * @property long The status in words, e.g. `Finished`.
     * @property timer Game clock while in progress.
     */
    @Serializable
    public data class Status(
        public val short: String? = null,
        public val long: String? = null,
        public val timer: String? = null,
    )

    @Serializable
    public data class League(
        public val id: Int,
        public val name: String,
        public val season: Int,
        public val logo: String? = null,
        public val country: Country? = null,
    )

    /** Points per quarter. Quarters not yet played (and overtime, if none) are null. */
    @Serializable
    public data class Score(
        @SerialName("quarter_1") public val quarter1: Int? = null,
        @SerialName("quarter_2") public val quarter2: Int? = null,
        @SerialName("quarter_3") public val quarter3: Int? = null,
        @SerialName("quarter_4") public val quarter4: Int? = null,
        public val overtime: Int? = null,
        public val total: Int? = null,
    )
}
