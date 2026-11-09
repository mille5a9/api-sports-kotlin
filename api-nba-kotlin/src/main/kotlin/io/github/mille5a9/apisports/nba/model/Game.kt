package io.github.mille5a9.apisports.nba.model

import kotlinx.serialization.Serializable

/**
 * Represents the response data for retrieving games from the NBA API.
 *
 * @param id The ID of the game.
 * @param league The league of the game.
 * @param season The season of the game.
 * @param date The date of the game.
 * @param stage The stage of the game.
 * @param status The status of the game.
 * @param periods The periods of the game.
 * @param arena The arena of the game.
 * @param teams The teams playing in the game.
 * @param scores The scores of the game.
 * @param officials The officials of the game.
 * @param timesTied The number of times the game was tied.
 * @param leadChanges The number of lead changes in the game.
 * @param nugget Additional information about the game.
 */
@Serializable
public data class Game(
    public val id: Int,
    public val league: String,
    public val season: Int,
    public val date: Date,
    public val stage: Int,
    public val status: Status,
    public val periods: Periods,
    public val arena: Arena,
    public val teams: Teams,
    public val scores: Scores,
    public val officials: List<String>,
    public val timesTied: Int?,
    public val leadChanges: Int?,
    public val nugget: String?,
) {
    /**
     * Represents a date with start and end timestamps and duration.
     *
     * @property start The start timestamp of the date.
     * @property end The end timestamp of the date.
     * @property duration The duration of the date.
     */
    @Serializable
    public data class Date(
        public val start: String,
        public val end: String?,
        public val duration: String?,
    )

    /**
     * Represents the status of a game.
     *
     * @property clock The current game clock time.
     * @property halftime Indicates if the game is currently at halftime.
     * @property short The short representation of the game status.
     * @property long The long representation of the game status.
     */
    @Serializable
    public data class Status(
        public val clock: String?,
        public val halftime: Boolean?,
        public val short: Int,
        public val long: String,
    )

    /**
     * Represents the period information of a game.
     *
     * @property current The current period number.
     * @property total The total number of periods in the game.
     * @property endOfPeriod Indicates if the current period has ended.
     */
    @Serializable
    public data class Periods(
        public val current: Int,
        public val total: Int,
        public val endOfPeriod: Boolean?,
    )

    /**
     * Represents an arena where a game is played.
     *
     * @property name The name of the arena.
     * @property city The city where the arena is located.
     * @property state The state where the arena is located.
     * @property country The country where the arena is located.
     */
    @Serializable
    public data class Arena(
        public val name: String?,
        public val city: String?,
        public val state: String?,
        public val country: String?,
    )

    /**
     * Represents the teams playing in a game.
     *
     * @property visitors The visiting team.
     * @property home The home team.
     */
    @Serializable
    public data class Teams(
        public val visitors: TeamSummary,
        public val home: TeamSummary,
    )

    /**
     * Represents the scores of a game.
     *
     * @property visitors The visiting team's score.
     * @property home The home team's score.
     */
    @Serializable
    public data class Scores(
        public val visitors: Score,
        public val home: Score,
    )

    /**
     * Represents the score of a team.
     *
     * @property win The number of wins.
     * @property loss The number of losses.
     * @property series The series score.
     * @property linescore The linescore of the game.
     * @property points The total number of points.
     */
    @Serializable
    public data class Score(
        public val win: Int?,
        public val loss: Int?,
        public val series: Series,
        public val linescore: List<String>,
        public val points: Int?,
    )

    /**
     * Represents the series score of a team.
     *
     * @property win The number of wins.
     * @property loss The number of losses.
     */
    @Serializable
    public data class Series(
        public val win: Int?,
        public val loss: Int?,
    )
}
