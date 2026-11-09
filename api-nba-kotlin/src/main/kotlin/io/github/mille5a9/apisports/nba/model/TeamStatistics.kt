package io.github.mille5a9.apisports.nba.model

import kotlinx.serialization.Serializable

/**
 * Represents the response object for team statistics.
 *
 * @property games The number of games played.
 * @property fastBreakPoints The number of points scored in fast breaks.
 * @property pointsInPaint The number of points scored in the paint.
 * @property biggestLead The biggest lead in points.
 * @property secondChancePoints The number of points scored from second chances.
 * @property pointsOffTurnovers The number of points scored from turnovers.
 * @property longestRun The length of the longest run in points.
 * @property points The total number of points scored.
 * @property fgm The total number of field goals made.
 * @property fga The total number of field goals attempted.
 * @property fgp The field goal percentage as a string.
 * @property ftm The total number of free throws made.
 * @property fta The total number of free throws attempted.
 * @property ftp The free throw percentage as a string.
 * @property tpm The total number of three-pointers made.
 * @property tpa The total number of three-pointers attempted.
 * @property tpp The three-point percentage as a string.
 * @property offReb The total number of offensive rebounds.
 * @property defReb The total number of defensive rebounds.
 * @property totReb The total number of rebounds.
 * @property assists The total number of assists.
 * @property pFouls The total number of personal fouls.
 * @property steals The total number of steals.
 * @property turnovers The total number of turnovers.
 * @property blocks The total number of blocks.
 * @property plusMinus The plus/minus of the team.
 */
@Serializable
public data class TeamStatistics(
    public val games: Int,
    public val fastBreakPoints: Int,
    public val pointsInPaint: Int,
    public val biggestLead: Int,
    public val secondChancePoints: Int,
    public val pointsOffTurnovers: Int,
    public val longestRun: Int,
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
    public val plusMinus: Int,
)
