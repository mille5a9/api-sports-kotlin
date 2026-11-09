package io.github.mille5a9.apisports.nba.model

import kotlinx.serialization.Serializable

@Serializable
public data class GameTeamStatistics(
    public val team: TeamSummary,
    public val statistics: List<Statistics>,
) {
    /**
     * Represents the statistics of a game.
     *
     * Advanced stats (fast break, paint, second chance, off turnovers, biggest lead, longest run)
     * are null for games the API has not tracked in detail.
     *
     * @property fastBreakPoints The number of fast break points.
     * @property pointsInPaint The number of points in the paint.
     * @property biggestLead The biggest lead in the game.
     * @property secondChancePoints The number of second chance points.
     * @property pointsOffTurnovers The number of points off turnovers.
     * @property longestRun The longest run in the game.
     * @property points The total number of points.
     * @property fgm The number of field goals made.
     * @property fga The number of field goals attempted.
     * @property fgp The field goal percentage as a string.
     * @property ftm The number of free throws made.
     * @property fta The number of free throws attempted.
     * @property ftp The free throw percentage as a string.
     * @property tpm The number of three-pointers made.
     * @property tpa The number of three-pointers attempted.
     * @property tpp The three-point percentage as a string.
     * @property offReb The number of offensive rebounds.
     * @property defReb The number of defensive rebounds.
     * @property totReb The total number of rebounds.
     * @property assists The number of assists.
     * @property pFouls The number of personal fouls.
     * @property steals The number of steals.
     * @property turnovers The number of turnovers.
     * @property blocks The number of blocks.
     * @property plusMinus The plus/minus of the team.
     * @property min The total minutes played, e.g. `240:00`.
     */
    @Serializable
    public data class Statistics(
        public val fastBreakPoints: Int?,
        public val pointsInPaint: Int?,
        public val biggestLead: Int?,
        public val secondChancePoints: Int?,
        public val pointsOffTurnovers: Int?,
        public val longestRun: Int?,
        public val points: Int,
        public val fgm: Int,
        public val fga: Int,
        public val fgp: String,
        public val ftm: Int,
        public val fta: Int,
        public val ftp: String,
        public val tpm: Int,
        public val tpa: Int,
        public val tpp: String,
        public val offReb: Int,
        public val defReb: Int,
        public val totReb: Int,
        public val assists: Int,
        public val pFouls: Int,
        public val steals: Int,
        public val turnovers: Int,
        public val blocks: Int,
        public val plusMinus: String,
        public val min: String,
    )
}
