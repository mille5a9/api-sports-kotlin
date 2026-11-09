package io.github.mille5a9.apisports.nba.model

import kotlinx.serialization.Serializable

/**
 * Represents a response object containing information about NBA teams.
 *
 * @param id The ID of the team.
 * @param name The name of the team.
 * @param nickname The nickname of the team.
 * @param code The code of the team.
 * @param city The city of the team.
 * @param logo The logo of the team.
 * @param allStar Indicates whether the team is an All-Star team.
 * @param nbaFranchise Indicates whether the team is an NBA franchise.
 * @param leagues The leagues associated with the team.
 */
@Serializable
public data class Team(
    public val id: Int,
    public val name: String,
    public val nickname: String?,
    public val code: String?,
    public val city: String?,
    public val logo: String?,
    public val allStar: Boolean,
    public val nbaFranchise: Boolean,
    public val leagues: Leagues,
) {
    /**
     * Represents a collection of different leagues associated with NBA teams.
     *
     * @property standard The standard league associated with the NBA teams.
     * @property africa The African league associated with the NBA teams. Can be null.
     * @property sacramento The Sacramento league associated with the NBA teams. Can be null.
     * @property vegas The Las Vegas league associated with the NBA teams. Can be null.
     * @property utah The Utah league associated with the NBA teams. Can be null.
     * @property orlando The Orlando league associated with the NBA teams. Can be null.
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
     * Represents a league associated with NBA teams.
     *
     * @property conference The conference associated with the NBA teams. Can be null.
     * @property division The division associated with the NBA teams. Can be null.
     */
    @Serializable
    public data class League(
        public val conference: String?,
        public val division: String?,
    )
}
