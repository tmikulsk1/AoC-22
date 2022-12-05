package day04

import readInput

/**
 * Advent of Code 2022 - Day 4
 * @author tmikulsk1
 */

fun main() {
    val inputCleanupSections = readInput("day04/input.txt").readLines()
    val cleanupSectionsRanges = splitCleanupSectionsInputByRanges(inputCleanupSections)

    getPuzzle1TotalContainedInRanges(cleanupSectionsRanges)
    getPuzzle2TotalCommonSections(cleanupSectionsRanges)
}

/**
 * Puzzle 1: get only pairs that are contained in range
 */
fun getPuzzle1TotalContainedInRanges(cleanupSectionsRanges: List<Pair<List<Int>, List<Int>>>) {
    var counter = 0
    cleanupSectionsRanges.forEach { section ->
        if (section.first.containsOrIsContainedIn(section.second)) {
            counter++
        }
    }
    println(counter)
}

/**
 * Puzzle 2: get pairs with have common sections
 */
fun getPuzzle2TotalCommonSections(cleanupSectionsRanges: List<Pair<List<Int>, List<Int>>>) {
    var counter = 0
    cleanupSectionsRanges.forEach { section ->
        val intersectValue = section.first.toSet() intersect section.second.toSet()
        if (intersectValue.isNotEmpty()) counter++
    }
    println(counter)
}

fun splitCleanupSectionsInputByRanges(inputCleanupSections: List<String>): List<Pair<List<Int>, List<Int>>> {
    return inputCleanupSections
        .map { section ->
            section.split(",")
        }
        .map(List<String>::toHashSet)
        .map { groupedSectionSet ->
            val firstGroup = groupedSectionSet.first().split("-")
            val secondGroup = groupedSectionSet.last().split("-")

            val firstGroupRange = listOf(
                firstGroup.firstInteger()..firstGroup.lastInteger()
            ).flatten()
            val secondGroupRange = listOf(
                secondGroup.firstInteger()..secondGroup.lastInteger()
            ).flatten()

            return@map Pair(firstGroupRange, secondGroupRange)
        }
}

fun List<Int>.containsOrIsContainedIn(listToCompare: List<Int>): Boolean {
    return containsAll(listToCompare) || listToCompare.containsAll(this)
}

fun List<String>.firstInteger() = first().toInt()

fun List<String>.lastInteger() = last().toInt()
