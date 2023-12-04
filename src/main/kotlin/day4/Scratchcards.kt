package day4

import java.io.File
import kotlin.math.pow

fun main(){
    val input: List<String> = File("src/main/resources/day4input.txt").useLines { it.toList() }

    println(part1(input))
    println(part2(input))
}

private fun part1(input: List<String>): Int {
    val winnings = mutableListOf<Int>()
    input.forEach {
        val stringsNumbers = it.substringAfter(":").split("|")
        val win = stringsNumbers[0].trim().split(" ")
        val myNumbers = stringsNumbers[1].trim().split(" ")
        winnings.add(myNumbers.count { n -> n.isNotEmpty() && win.contains(n) })
    }
    var sum = 0
    winnings.filter { it > 0 }.forEach {
        sum+= 2.0.pow(it - 1).toInt()
    }
    return sum
}

fun part2(input: List<String>): Int {
    val copiesPerIndex =  MutableList(input.size) { 1 }
    val cards = input.map { Card(it) }
    cards.forEachIndexed { index, card ->
        (1..card.winningsCount).filter { index + it < copiesPerIndex.size }.forEach {
            copiesPerIndex[index + it] += copiesPerIndex[index]
        }
    }
    return copiesPerIndex.sum()
}

class Card(s: String) {
    val winningsCount: Int
    init {
        val numbers = s.replace(".*: ".toRegex(), "").split(" | ").map {
            it.split(" ").filter(String::isNotBlank).map(String::toInt)
        }
        winningsCount = numbers[0].intersect(numbers[1].toSet()).size
    }
}