package io.github.mille5a9.apisports.nba.model

import kotlinx.serialization.Serializable

/**
 * Represents the response object for player statistics.
 *
 * @param player The player object containing player information.
 * @param team The team object containing team information.
 * @param game The game object containing game information.
 * @param points The total number of points scored by the player.
 * @param pos The position of the player.
 * @param min The number of minutes played by the player.
 * @param fgm The total number of field goals made by the player.
 * @param fga The total number of field goals attempted by the player.
 * @param fgp The field goal percentage of the player.
 * @param ftm The total number of free throws made by the player.
 * @param fta The total number of free throws attempted by the player.
 * @param ftp The free throw percentage of the player.
 * @param tpm The total number of three-point field goals made by the player.
 * @param tpa The total number of three-point field goals attempted by the player.
 * @param tpp The three-point field goal percentage of the player.
 * @param offReb The number of offensive rebounds by the player.
 * @param defReb The number of defensive rebounds by the player.
 * @param totReb The total number of rebounds by the player.
 * @param assists The total number of assists by the player.
 * @param pFouls The total number of personal fouls by the player.
 * @param steals The total number of steals by the player.
 * @param turnovers The total number of turnovers by the player.
 * @param blocks The total number of blocks by the player.
 * @param plusMinus The plus/minus of the player.
 */
@Serializable
public data class PlayerStatistics(
    public val player: PlayerRef,
    public val team: TeamSummary,
    public val game: GameRef,
    public val points: Int?,
    public val pos: String?,
    public val min: String?,
    public val fgm: Int?,
    public val fga: Int?,
    public val fgp: String?,
    public val ftm: Int?,
    public val fta: Int?,
    public val ftp: String?,
    public val tpm: Int?,
    public val tpa: Int?,
    public val tpp: String?,
    public val offReb: Int?,
    public val defReb: Int?,
    public val totReb: Int?,
    public val assists: Int?,
    public val pFouls: Int?,
    public val steals: Int?,
    public val turnovers: Int?,
    public val blocks: Int?,
    public val plusMinus: String?,
    public val comment: String? = null,
) {
    /**
     * Represents a player in an NBA team.
     *
     * @property id The unique identifier of the player.
     * @property firstname The firstname of the player.
     * @property lastname The lastname of the player.
     */
    @Serializable
    public data class PlayerRef(
        public val id: Int,
        public val firstname: String,
        public val lastname: String,
    )

    /**
     * Represents a game in the NBA.
     *
     * @property id The unique identifier of the game.
     */
    @Serializable
    public data class GameRef(
        public val id: Int,
    )
}
