package io.github.mille5a9.apisports.nba.model

import kotlinx.serialization.Serializable

/**
 * Represents a response object for retrieving standings information.
 *
 * @param league The name of the league.
 * @param season The season.
 * @param team The team.
 * @param conference The conference.
 * @param division The division.
 * @param win The win record.
 * @param loss The loss record.
 * @param gamesBehind The games behind.
 * @param streak The streak.
 * @param winStreak The win streak status.
 * @param tieBreakerPoints The tie-breaker points (can be null).
 */
@Serializable
public data class Standing(
    public val league: String,
    public val season: Int,
    public val team: TeamSummary,
    public val conference: Conference,
    public val division: Division,
    public val win: Record,
    public val loss: Record,
    public val gamesBehind: String?,
    public val streak: Int?,
    public val winStreak: Boolean,
    public val tieBreakerPoints: Int?,
) {
    /**
     * Represents a conference in the NBA.
     *
     * @property name The name of the conference.
     * @property rank The rank of the conference.
     * @property win The number of wins.
     * @property loss The number of losses.
     */
    @Serializable
    public data class Conference(
        public val name: String,
        public val rank: Int,
        public val win: Int,
        public val loss: Int,
    )

    /**
     * Represents a division in the NBA.
     *
     * @property name The name of the division.
     * @property rank The rank of the division.
     * @property win The number of wins.
     * @property loss The number of losses.
     * @property gamesBehind The games behind as a string.
     */
    @Serializable
    public data class Division(
        public val name: String,
        public val rank: Int,
        public val win: Int,
        public val loss: Int,
        public val gamesBehind: String?,
    )

    /**
     * Represents a record in the NBA standings.
     *
     * @property home The number of home wins.
     * @property away The number of away wins.
     * @property total The total number of wins.
     * @property percentage The win percentage as a string.
     * @property lastTen The number of wins in the last ten games.
     */
    @Serializable
    public data class Record(
        public val home: Int,
        public val away: Int,
        public val total: Int,
        public val percentage: String,
        public val lastTen: Int,
    )
}
