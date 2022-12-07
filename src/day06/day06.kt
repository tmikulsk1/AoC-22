package day06

import readInput

/**
 * Advent of Code 2022 - Day 6
 * @author tmikulsk1
 */

fun main() {
    val inputSignal = readInput("day06/input.txt").readLines()

    val chunkSizeForPuzzle1 = 4
    val signalChunksForPuzzle1 = getSignalChunks(inputSignal, chunkSizeForPuzzle1)
    getMessageBeginningPosition(signalChunksForPuzzle1, chunkSizeForPuzzle1)

    val chunkSizeForPuzzle2 = 14
    val signalChunksForPuzzle2 = getSignalChunks(inputSignal, chunkSizeForPuzzle2)
    getMessageBeginningPosition(signalChunksForPuzzle2, chunkSizeForPuzzle2)
}

fun getMessageBeginningPosition(
    signalChunks: List<List<Char>>,
    chunkSize: Int
) = with(signalChunks) {
    forEachIndexed { index, chunk ->
        if (chunk.toSet().size == chunkSize) {
            val signalBeginningAt = index + chunkSize
            println("Message begins at position: $signalBeginningAt")
            return@with
        }
    }
}

fun getSignalChunks(inputSignal: List<String>, chunkSize: Int): List<List<Char>> {
    return inputSignal.formatInputToCharList().getChunksBy(chunkSize)
}

fun List<String>.formatInputToCharList(): List<Char> = flatMap(CharSequence::toList)

fun List<Char>.getChunksBy(size: Int) = windowed(size, 1)
