package day3

import java.io.File

fun main() {
    /**
     * adding one empty column to the end of the input for easier handling of edge cases in identifying the numbers.
     */
    val input: List<String> = File("src/main/resources/day3input.txt").useLines { it.toList() }.map { "$it." }

    println(part1(input))
    println(part2(input))
}

fun Char.isSymbol(): Boolean {
    val symbols = listOf(
        '!', '"', '#', '$', '%', '&', '\'', '(', ')', '*', '+',
        ',', '-', '/', ':', ';', '<', '=', '>', '?', '@',
        '[', '\\', ']', '^', '_', '`', '{', '|', '}', '~'
    )

    return symbols.contains(this)
}

fun part1(input: List<String>): Int {
    var sum = 0
    input.forEachIndexed { i, s ->
        var number = ""
        s.toList().forEachIndexed { j, c ->

            if (c.isDigit()) {
                number += c
            } else {
                if (number.isNotEmpty()) {
                    var isPartNumber = false
                    val numbjStart = j - number.length
                    val numbjEnd = j - 1

                    if ((numbjStart - 1) >= 0 && (numbjEnd + 1) < s.length && (i - 1) >= 0 && (i + 1) < input.size) {
                        ((numbjStart - 1)..(numbjEnd + 1)).forEach {
                            if (input[i - 1][it].isSymbol() || input[i + 1][it].isSymbol()) {
                                isPartNumber = true
                            }
                        }

                        if (input[i][numbjStart - 1].isSymbol() || input[i][numbjEnd + 1].isSymbol()) {
                            isPartNumber = true
                        }
                    } else if (i == 0 && (numbjStart - 1) >= 0 && (numbjEnd + 1) < s.length) {
                        ((numbjStart - 1)..(numbjEnd + 1)).forEach {
                            if (input[1][it].isSymbol()) {
                                isPartNumber = true
                            }
                        }

                        if (input[i][numbjStart - 1].isSymbol() || input[i][numbjEnd + 1].isSymbol()) {
                            isPartNumber = true
                        }
                    } else if (i == input.size - 1 && (numbjStart - 1) >= 0 && (numbjEnd + 1) < s.length) {
                        ((numbjStart - 1)..(numbjEnd + 1)).forEach {
                            if (input[i - 1][it].isSymbol()) {
                                isPartNumber = true
                            }
                        }

                        if (input[i][numbjStart - 1].isSymbol() || input[i][numbjEnd + 1].isSymbol()) {
                            isPartNumber = true
                        }
                    } else if (numbjStart == 0 && (i - 1) >= 0 && (i + 1) < input.size) {
                        ((0)..(numbjEnd + 1)).forEach {
                            if (input[i - 1][it].isSymbol() || input[i + 1][it].isSymbol()) {
                                isPartNumber = true
                            }
                        }

                        if (input[i][numbjEnd + 1].isSymbol()) {
                            isPartNumber = true
                        }
                    } else if (numbjEnd == s.length - 1 && (i - 1) >= 0 && (i + 1) < input.size) {
                        ((numbjStart - 1)..(numbjEnd)).forEach {
                            if (input[i - 1][it].isSymbol() || input[i + 1][it].isSymbol()) {
                                isPartNumber = true
                            }
                        }

                        if (input[i][numbjStart - 1].isSymbol()) {
                            isPartNumber = true
                        }
                    } else if (i == 0 && numbjStart == 0) {
                        ((0)..(numbjEnd + 1)).forEach {
                            if (input[1][it].isSymbol()) {
                                isPartNumber = true
                            }
                        }

                        if (input[i][numbjEnd + 1].isSymbol()) {
                            isPartNumber = true
                        }
                    } else if (i == 0 && numbjEnd == s.length - 1) {
                        ((numbjStart - 1)..(numbjEnd)).forEach {
                            if (input[1][it].isSymbol()) {
                                isPartNumber = true
                            }
                        }

                        if (input[i][numbjStart - 1].isSymbol()) {
                            isPartNumber = true
                        }
                    } else if (i == input.size - 1 && numbjStart == 0) {
                        ((0)..(numbjEnd + 1)).forEach {
                            if (input[i - 1][it].isSymbol()) {
                                isPartNumber = true
                            }
                        }

                        if (input[i][numbjEnd + 1].isSymbol()) {
                            isPartNumber = true
                        }
                    } else if (i == input.size - 1 && numbjEnd == s.length - 1) {
                        ((numbjStart - 1)..(numbjEnd)).forEach {
                            if (input[i - 1][it].isSymbol()) {
                                isPartNumber = true
                            }
                        }

                        if (input[i][numbjStart - 1].isSymbol()) {
                            isPartNumber = true
                        }
                    }


                    if (isPartNumber) {
                        sum += number.toInt()
                    }
                }
                number = ""
            }

        }
    }

    return sum
}

fun part2(input: List<String>): Long {
    val starsIndexes = mutableListOf<Pair<Int, Int>>()
    val numbersIndexes = mutableListOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()

    //fill the indexing lists
    input.forEachIndexed { i, s ->
        var number = ""
        s.forEachIndexed { j, c ->
            if (c == '*') {
                starsIndexes.add(Pair(i, j))
            }
            if (c.isDigit()) {
                number += c
            } else {
                if (number.isNotEmpty()) {
                    val numbjStart = j - number.length
                    val numbjEnd = j - 1
                    numbersIndexes.add(Pair(Pair(i, numbjStart), Pair(i, numbjEnd)))

                    number = ""
                }
            }
        }
    }

    return calculateProductOfAdjacentNumbers(input, starsIndexes, numbersIndexes)
}

fun calculateProductOfAdjacentNumbers(
    table: List<String>,
    starPositions: List<Pair<Int, Int>>,
    numberRanges: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>
): Long {
    var productsSum = 0L

    for ((starRow, starCol) in starPositions) {
        var adjacentCount = 0
        var product = 1L

        for ((numStart, numEnd) in numberRanges) {
            if (isAdjacent(starRow, starCol, numStart, numEnd)) {
                adjacentCount++
                var num = ""

                (numStart.second..numEnd.second).forEach {
                    num += table[numStart.first][it]
                }
                product *= num.toInt()
            }
        }
        if (adjacentCount == 2) {
            productsSum += product
        }
    }

    return productsSum
}

fun isAdjacent(starRow: Int, starCol: Int, numStart: Pair<Int, Int>, numEnd: Pair<Int, Int>) =
            (starRow - 1 == numStart.first && starCol in (numStart.second - 1..numEnd.second + 1)) ||
            (starRow == numStart.first && (starCol - 1 == numEnd.second || starCol + 1 == numStart.second)) ||
            (starRow + 1 == numStart.first && starCol in (numStart.second - 1..numEnd.second + 1))