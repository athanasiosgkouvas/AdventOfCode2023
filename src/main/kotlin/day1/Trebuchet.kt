package day1

import java.io.File

fun main(args: Array<String>) {
    val strings: List<String> = File("src/main/resources/day1input.txt").useLines { it.toList() }
    var sum = 0

    strings.forEach { string->
        val numbers = string.getRealDigits()
        val n = "${numbers.first()}${numbers.last()}"
        sum += n.toInt()
    }
    println(sum)
}

fun String.getRealDigits(): List<Char> {
    val spelledDigits = mapOf(
        "one" to '1',
        "two" to '2',
        "three" to '3',
        "four" to '4',
        "five" to '5',
        "six" to '6',
        "seven" to '7',
        "eight" to '8',
        "nine" to '9'
    )

    val result = StringBuilder()

    this.forEachIndexed { index, c ->
        when {
            c.isDigit() -> result.append(c)
            spelledDigits.any { this.startsWith(it.key, index) } -> {
                val matchingEntry = spelledDigits.entries.first { this.startsWith(it.key, index) }
                result.append(matchingEntry.value)
            }
        }
    }

    return result.filter { it.isDigit() }.toList()
}

