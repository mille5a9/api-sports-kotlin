package io.github.mille5a9.apisports.samples

import io.github.mille5a9.apisports.core.ApiSportsException
import io.github.mille5a9.apisports.core.Host
import io.github.mille5a9.apisports.nba.ApiNbaClient
import io.github.mille5a9.apisports.nba.NbaParameters
import io.github.mille5a9.apisports.nfl.ApiNflClient
import io.github.mille5a9.apisports.nfl.model.get
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlin.system.exitProcess

private const val USAGE = """Usage:
  status                  account and quota for your key
  nba standings <season>  NBA conference standings, e.g. 2024 for 2024-25
  nfl standings <season>  NFL division standings
  nfl game <gameId>       NFL box score: line score, scoring plays, passing

Set API_SPORTS_KEY (and API_SPORTS_HOST=rapidapi for a RapidAPI key)."""

fun main(args: Array<String>) {
    val key = System.getenv("API_SPORTS_KEY") ?: fail("API_SPORTS_KEY is not set.\n\n$USAGE")
    val host = if (System
            .getenv(
                "API_SPORTS_HOST",
            ).equals("rapidapi", ignoreCase = true)
    ) {
        Host.RAPID_API
    } else {
        Host.API_SPORTS
    }

    try {
        runBlocking {
            when (args.toList()) {
                listOf("status") -> status(key, host)
                listOf("nba", "standings", args.getOrNull(2)) -> nbaStandings(key, host, args[2].toSeason())
                listOf("nfl", "standings", args.getOrNull(2)) -> nflStandings(key, host, args[2].toSeason())
                listOf("nfl", "game", args.getOrNull(2)) -> nflGame(key, host, args[2].toIntOrNull() ?: fail(USAGE))
                else -> fail(USAGE)
            }
        }
    } catch (e: ApiSportsException) {
        fail("Request failed: ${e.message}")
    }
}

private suspend fun status(
    key: String,
    host: Host,
) = ApiNbaClient(key, host).use { client ->
    val status = client.getAccountStatus().response
    println(
        "Plan: ${status.subscription.plan} (active: ${status.subscription.active}, ends ${status.subscription.end})",
    )
    println("Requests today: ${status.requests.current} / ${status.requests.limitDay}")
}

private suspend fun nbaStandings(
    key: String,
    host: Host,
    season: Int,
) = ApiNbaClient(key, host).use { nba ->
    nba
        .getStandings(NbaParameters(league = "standard", season = season))
        .response
        .groupBy { it.conference.name }
        .toSortedMap()
        .forEach { (conference, teams) ->
            println("\n${conference.replaceFirstChar(Char::uppercase)}ern Conference $season-${(season + 1) % 100}")
            println("%3s  %-26s %5s %6s %5s".format("#", "Team", "W-L", "Pct", "GB"))
            teams.sortedBy { it.conference.rank }.forEach {
                println(
                    "%3d  %-26s %5s %6s %5s".format(
                        it.conference.rank,
                        it.team.name,
                        "${it.win.total}-${it.loss.total}",
                        it.win.percentage,
                        it.gamesBehind ?: "-",
                    ),
                )
            }
        }
}

private suspend fun nflStandings(
    key: String,
    host: Host,
    season: Int,
) = ApiNflClient(key, host).use { nfl ->
    nfl
        .getStandings(ApiNflClient.NFL, season)
        .response
        .groupBy { it.division.orEmpty() }
        .toSortedMap()
        .forEach { (division, teams) ->
            println("\n$division")
            teams.sortedBy { it.position }.forEach {
                val record = listOfNotNull(it.won, it.lost, it.ties?.takeIf { ties -> ties > 0 }).joinToString("-")
                println(
                    "  %d. %-24s %-7s PF %3s  PA %3s  %s".format(
                        it.position,
                        it.team.name,
                        record,
                        it.points?.scored,
                        it.points?.against,
                        it.streak.orEmpty(),
                    ),
                )
            }
        }
}

private suspend fun nflGame(
    key: String,
    host: Host,
    gameId: Int,
) = ApiNflClient(key, host).use { nfl ->
    coroutineScope {
        // The three requests are independent, so run them concurrently.
        val game = async { nfl.getGameById(gameId).response.singleOrNull() }
        val events = async { nfl.getGameEvents(gameId).response }
        val passing = async { nfl.getGamePlayerStatistics(gameId, group = "Passing").response }

        val g = game.await() ?: fail("No game with id $gameId")
        println(
            "${g.teams.away.name} @ ${g.teams.home.name} - ${g.game.week}, ${g.game.date.date} (${g.game.status.long})",
        )
        println(
            g.game.venue
                ?.let { "${it.name}, ${it.city}" }
                .orEmpty(),
        )

        println("\n%-24s %3s %3s %3s %3s %3s  %3s".format("", "Q1", "Q2", "Q3", "Q4", "OT", "T"))
        listOf(g.teams.away to g.scores.away, g.teams.home to g.scores.home).forEach { (team, s) ->
            val cells = listOf(s.quarter1, s.quarter2, s.quarter3, s.quarter4, s.overtime).map { it?.toString() ?: "-" }
            println("%-24s %3s %3s %3s %3s %3s  %3s".format(team.name, *cells.toTypedArray(), s.total ?: "-"))
        }

        println("\nScoring plays")
        events.await().forEach { e ->
            println("  ${e.quarter} ${e.minute}  ${e.score?.away}-${e.score?.home}  ${e.type}: ${e.comment}")
        }

        println("\nPassing")
        passing.await().forEach { team ->
            team.groups.flatMap { it.players }.forEach { line ->
                val s = line.statistics
                println(
                    "  %-22s %-24s %6s  %4s yds  %s TD  %s INT".format(
                        line.player.name,
                        team.team.name,
                        s["comp att"],
                        s["yards"],
                        s["passing touch downs"],
                        s["interceptions"],
                    ),
                )
            }
        }
    }
}

private fun String.toSeason(): Int =
    toIntOrNull()?.takeIf { it in 1990..2100 } ?: fail("Invalid season: $this\n\n$USAGE")

private fun fail(message: String): Nothing {
    System.err.println(message)
    exitProcess(1)
}
