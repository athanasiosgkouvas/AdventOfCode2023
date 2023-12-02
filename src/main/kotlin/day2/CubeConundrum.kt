package day2

import java.io.File

fun main() {
    val gamesLines: List<String> = File("src/main/resources/day2input.txt").useLines { it.toList() }
    val games: List<Game> = gamesLines.toGames()

    /**
     * This is the part 1 solution
     *
     * val validGames = games.filter { game ->
     *         game.randomPicks.all { pick ->
     *             pick.reds <= 12 && pick.greens<= 13 && pick.blues <= 14
     *         }
     *     }
     *     var sumId = 0
     *     validGames.forEach {
     *         println(it)
     *         sumId += it.id
     *     }
     *
     *     println(sumId)
     */

    //Part 2

    var sum = 0
    games.forEach { game ->
        var leastRed = game.randomPicks.first().reds
        var leastGreen = game.randomPicks.first().greens
        var leastBlue = game.randomPicks.first().blues

        game.randomPicks.forEach {
            if (it.reds > leastRed) {
                leastRed = it.reds
            }
            if (it.greens > leastGreen) {
                leastGreen = it.greens
            }
            if (it.blues > leastBlue) {
                leastBlue = it.blues
            }
        }

        val power = leastRed * leastGreen * leastBlue
        println(power)
        sum += power
    }

    println(sum)

}

fun List<String>.toGames(): List<Game> {
    return this.map { str ->
        val gameId = str.substringBefore(':').filter { it.isDigit() }.map { it.digitToInt() }
        var gameIdStr = ""

        gameId.forEach {
            gameIdStr += it
        }

        val picksString = str.substringAfter(':')
        val picks = picksString.split(";").map { it.trim() }
        val randomPicks = picks.toRandomPicks()

        Game(
            id = gameIdStr.toInt(),
            randomPicks = randomPicks
        )
    }
}

fun List<String>.toRandomPicks(): List<RandomPick> {
    return this.map { str ->
        val colorPicks = str.split(",").map { it.trim() }

        var numReds = 0
        var numGreens = 0
        var numBlues = 0

        colorPicks.forEach { colorPick ->
            when {
                colorPick.contains("red") -> numReds += colorPick.filter { it.isDigit() }.toInt()
                colorPick.contains("green") -> numGreens += colorPick.filter { it.isDigit() }.toInt()
                colorPick.contains("blue") -> numBlues += colorPick.filter { it.isDigit() }.toInt()
            }
        }

        RandomPick(
            reds = numReds,
            greens = numGreens,
            blues = numBlues
        )
    }
}

data class Game(
    val id: Int,
    val randomPicks: List<RandomPick>
)

data class RandomPick(
    val reds: Int = 0,
    val greens: Int = 0,
    val blues: Int = 0,
)