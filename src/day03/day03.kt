package day03

import readInput

/**
 * Advent of Code 2022 - Day 3
 * @author tmikulsk1
 */

fun main() {

    val inputRucksacks = readInput("day03/input.txt").readLines()

    getPuzzle1PriorityRucksacks(inputRucksacks)
}

/**
 * Puzzle 1: show priority rucksacks
 */
fun getPuzzle1PriorityRucksacks(inputRucksacks: List<String>) {
    val alphabetList = getAlphabetLowerAndUpperCaseList()
    val prioritySum = getRucksackPrioritySum(inputRucksacks, alphabetList)

    println(prioritySum)
}

fun getRucksackPrioritySum(
    inputRucksacks: List<String>,
    alphabetList: List<Char>
): Int {
    var prioritySum = 0

    inputRucksacks.splitRucksacksInHalf().forEach { sack ->

        val rucksackKeySet = sack.keys.first().toSet()
        val rucksackValueSet = sack.values.first().toSet()
        val commonLetter = rucksackKeySet.intersect(rucksackValueSet)
        val index = alphabetList.indexOf(commonLetter.first())

        prioritySum += index + 1
    }

    return prioritySum
}

fun List<String>.splitRucksacksInHalf(): List<HashMap<String, String>> = map { rucksack ->
    val halfRucksack = rucksack.length / 2
    val firstHalfPart = rucksack.substring(0, halfRucksack)
    val secondHalfPart = rucksack.substring(halfRucksack)
    return@map hashMapOf(firstHalfPart to secondHalfPart)
}

fun getAlphabetLowerAndUpperCaseList(): List<Char> =
    listOf('a'..'z', 'A'..'Z').flatten().toList()