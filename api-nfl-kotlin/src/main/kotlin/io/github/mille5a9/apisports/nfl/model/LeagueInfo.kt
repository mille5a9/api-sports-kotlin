package io.github.mille5a9.apisports.nfl.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A league with the seasons available for it, from `leagues`.
 * The API has two: NFL (id 1) and NCAA (id 2).
 */
@Serializable
public data class LeagueInfo(
    public val league: League,
    public val country: Country? = null,
    public val seasons: List<Season> = emptyList(),
) {
    @Serializable
    public data class League(
        public val id: Int,
        public val name: String,
        public val logo: String? = null,
    )

    /**
     * @property year The season, e.g. `2024` for the 2024-25 season.
     * @property start First day of the season, `YYYY-MM-DD`.
     * @property end Last day of the season, `YYYY-MM-DD`.
     * @property coverage Which endpoints have data for this season.
     */
    @Serializable
    public data class Season(
        public val year: Int,
        public val start: String? = null,
        public val end: String? = null,
        public val current: Boolean = false,
        public val coverage: Coverage? = null,
    )

    @Serializable
    public data class Coverage(
        public val games: GamesCoverage? = null,
        public val statistics: StatisticsCoverage? = null,
        public val players: Boolean = false,
        public val injuries: Boolean = false,
        public val standings: Boolean = false,
    )

    @Serializable
    public data class GamesCoverage(
        public val events: Boolean = false,
        // Misspelled by the API.
        @SerialName("statisitcs") public val statistics: GameStatisticsCoverage? = null,
    )

    @Serializable
    public data class GameStatisticsCoverage(
        public val teams: Boolean = false,
        public val players: Boolean = false,
    )

    @Serializable
    public data class StatisticsCoverage(
        public val season: SeasonStatisticsCoverage? = null,
    )

    @Serializable
    public data class SeasonStatisticsCoverage(
        public val players: Boolean = false,
    )
}
