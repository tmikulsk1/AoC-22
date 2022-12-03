package day02

import day02.Constants.CommandSymbols
import day02.Constants.DEFAULT_VALUE
import day02.Constants.DRAW_EARNING_POINT
import day02.Constants.InterpretedSymbols
import day02.Constants.PAPER_POINT_VALUE
import day02.Constants.PAPER_SYMBOL
import day02.Constants.ROCK_POINT_VALUE
import day02.Constants.ROCK_SYMBOL
import day02.Constants.SCISSOR_POINT_VALUE
import day02.Constants.SCISSOR_SYMBOL
import day02.Constants.WIN_EARNING_POINT
import readInput

/**
 * Advent of Code 2022
 * Day 1 / Puzzle 1
 * @author tmikulsk1
 *
 */

fun main() {
    val inputGame = readInput("day02/input.txt").readLines()

    getPuzzle1TotalScore(inputGame)
    getPuzzle2TotalScore(inputGame)
}

/**
 * Puzzle1: get total score and print
 */
fun getPuzzle1TotalScore(inputGameChart: List<String>) {
    val matches = getIncompleteConvertedGameChart(inputGameChart)
    val totalScore = sumTotalScore(matches)
    println(totalScore)
}

/**
 * Puzzle2: get total score and print
 */
fun getPuzzle2TotalScore(inputGameChart: List<String>) {
    val matches = getFullConvertedGameChart(inputGameChart)
    val totalScore = sumTotalScore(matches)
    println(totalScore)
}

/**
 * Paper, Rock & Scissor game logic sum total score
 * from ultra top secret strategy guide
 */
fun sumTotalScore(matches: List<HashMap<Int, Int>>): Int {
    var score = 0
    matches.forEachIndexed { index, match ->

        val opponentValue = match.keys.first()
        val ownValue = match.values.first()
        val earningPoint = getEarningPoint(opponentValue, ownValue)

        score += earningPoint + ownValue
    }
    return score
}

/**
 * When next (from opponent) in circular list from Paper, Rock & Scissor logic is
 * equals with my own value, opponent wins
 *
 * When both values are equal, is a draw
 *
 * When next (from opponent) in circular list from Paper, Rock & Scissor logic is
 * difference with my own value, I win
 */
fun getEarningPoint(opponentValue: Int, ownValue: Int): Int {
    val next = getNextValueInCircularCycle(opponentValue)
    return if (opponentValue == ownValue) {
        // WE DRAW
        DRAW_EARNING_POINT
    } else if (next == ownValue) {
        // I WON
        WIN_EARNING_POINT
    } else {
        // I LOST
        DEFAULT_VALUE
    }
}

/**
 * Return the value from the circular list in Paper, Rock & Scissor sequence
 */
fun getNextValueInCircularCycle(opponentValue: Int): Int {
    val cycle = listOf(ROCK_POINT_VALUE, PAPER_POINT_VALUE, SCISSOR_POINT_VALUE)

    val index = cycle.indexOf(opponentValue)
    return if (cycle.size - 1 == index) {
        cycle[0]
    } else {
        cycle[index + 1]
    }
}

/**
 * Puzzle 1: without knowing the meaning of the second element from match
 */
fun getIncompleteConvertedGameChart(inputGameChart: List<String>): List<HashMap<Int, Int>> {
    return inputGameChart.splitGameChart().map { value ->
        val opponentValue = value[0].convertSymbolToPointValue()
        val ownValue = value[1].convertInterpretedSymbolToPointValue()
        hashMapOf(opponentValue to ownValue)
    }
}

/**
 * Puzzle 2: with all instructions for the match
 */
fun getFullConvertedGameChart(inputGameChart: List<String>): List<HashMap<Int, Int>> {
    return inputGameChart.splitGameChart().map { value ->
        val opponentValue = value[0].convertSymbolToPointValue()
        val ownValue = value[1].convertCommandSymbolToPointValue(opponentValue)
        hashMapOf(opponentValue to ownValue)
    }
}

fun List<String>.splitGameChart(): List<List<String>> = map { line ->
    line.split(" ")
}

fun String.convertSymbolToPointValue(): Int {
    return when {
        compareString(ROCK_SYMBOL) -> ROCK_POINT_VALUE
        compareString(PAPER_SYMBOL) -> PAPER_POINT_VALUE
        compareString(SCISSOR_SYMBOL) -> SCISSOR_POINT_VALUE
        else -> DEFAULT_VALUE
    }
}

fun String.convertInterpretedSymbolToPointValue(): Int {
    return when {
        compareString(InterpretedSymbols.ROCK_SYMBOL) -> ROCK_POINT_VALUE
        compareString(InterpretedSymbols.PAPER_SYMBOL) -> PAPER_POINT_VALUE
        compareString(InterpretedSymbols.SCISSOR_SYMBOL) -> SCISSOR_POINT_VALUE
        else -> DEFAULT_VALUE
    }
}

fun String.convertCommandSymbolToPointValue(opponentValue: Int): Int {
    return when {
        compareString(CommandSymbols.DRAW_SYMBOL) -> opponentValue
        compareString(CommandSymbols.LOSE_SYMBOL) -> {
            return when (opponentValue) {
                ROCK_POINT_VALUE -> SCISSOR_POINT_VALUE
                PAPER_POINT_VALUE -> ROCK_POINT_VALUE
                SCISSOR_POINT_VALUE -> PAPER_POINT_VALUE
                else -> DEFAULT_VALUE
            }
        }

        compareString(CommandSymbols.WIN_SYMBOL) -> {
            return when (opponentValue) {
                ROCK_POINT_VALUE -> PAPER_POINT_VALUE
                PAPER_POINT_VALUE -> SCISSOR_POINT_VALUE
                SCISSOR_POINT_VALUE -> ROCK_POINT_VALUE
                else -> DEFAULT_VALUE
            }
        }

        else -> DEFAULT_VALUE
    }
}

fun String.compareString(value: String) = equals(value, ignoreCase = true)

object Constants {

    const val ROCK_POINT_VALUE = 1
    const val PAPER_POINT_VALUE = 2
    const val SCISSOR_POINT_VALUE = 3
    const val DEFAULT_VALUE = 0

    const val ROCK_SYMBOL = "A"
    const val PAPER_SYMBOL = "B"
    const val SCISSOR_SYMBOL = "C"

    const val WIN_EARNING_POINT = 6
    const val DRAW_EARNING_POINT = 3

    /**
     * Puzzle 1: interpreted strategy guide
     */
    object InterpretedSymbols {

        const val ROCK_SYMBOL = "X"
        const val PAPER_SYMBOL = "Y"
        const val SCISSOR_SYMBOL = "Z"
    }

    /**
     * Puzzle 2: presented full strategy guide
     */
    object CommandSymbols {

        const val LOSE_SYMBOL = "X"
        const val DRAW_SYMBOL = "Y"
        const val WIN_SYMBOL = "Z"
    }
}
