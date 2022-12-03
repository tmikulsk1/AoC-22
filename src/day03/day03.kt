package day03

import readInput

/**
 * Advent of Code 2022 - Day 3
 * @author tmikulsk1
 */

fun main() {
    val inputRucksacks = readInput("day03/input.txt").readLines()

    getPuzzle1PriorityRucksacks(inputRucksacks)
    getPuzzle2PriorityRucksacks(inputRucksacks)
}

/**
 * Puzzle 1: show priority from half grouped rucksacks
 */
fun getPuzzle1PriorityRucksacks(inputRucksacks: List<String>) {
    val alphabetList = getAlphabetLowerAndUpperCaseList()
    val prioritySum = getRucksackSplitInInHalfPrioritySum(inputRucksacks, alphabetList)

    println(prioritySum)
}

/**
 * Puzzle 2: show priority third grouped rucksacks
 */
fun getPuzzle2PriorityRucksacks(inputRucksacks: List<String>) {
    val alphabetList = getAlphabetLowerAndUpperCaseList()
    val rucksacks = separateRucksackByThree(inputRucksacks)
    val prioritySum = getRucksackSplitInThirdPrioritySum(rucksacks, alphabetList)

    println(prioritySum)
}

fun getRucksackSplitInThirdPrioritySum(
    inputRucksacks: List<List<String>>,
    alphabetList: List<Char>
): Int {
    var prioritySum = 0

    inputRucksacks.forEach { threeRuckSacks ->
        val firstRucksackPart = threeRuckSacks[0].toHashSet()
        val secondRucksackPart = threeRuckSacks[1].toHashSet()
        val thirdRucksackPart = threeRuckSacks[2].toHashSet()

        val commonLetter = firstRucksackPart intersect secondRucksackPart intersect thirdRucksackPart
        val index = alphabetList.indexOf(commonLetter.first())

        prioritySum += index + 1
    }

    return prioritySum
}

fun separateRucksackByThree(inputRucksacks: List<String>): List<List<String>> =
    inputRucksacks.chunked(3)

fun getRucksackSplitInInHalfPrioritySum(
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