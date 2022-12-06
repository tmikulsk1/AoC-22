package day05

import readInput

fun main() {
    val inputFile = readInput("day05/input.txt").readLines()
    val indexReference = inputFile.indexOf("")
    val commands = getCommandsForStackMoves(inputFile, indexReference)

    val arrayStacksForPuzzle1 = getSupplyStacks(inputFile, indexReference)
    getPuzzle1TopStackFromEachColumn(commands, arrayStacksForPuzzle1)

    val arrayStacksForPuzzle2 = getSupplyStacks(inputFile, indexReference)
    getPuzzle2TopStackFromEachColumn(commands, arrayStacksForPuzzle2)
}

/**
 * Puzzle 1: get top from column
 */
fun getPuzzle1TopStackFromEachColumn(
    commands: List<List<Int>>,
    arrayStacks: MutableList<MutableList<String>>
) {
    val movedStacks = moveStacksAccordingToCommands(
        commands,
        arrayStacks,
        shouldChangePileOrder = false
    )

    println("Formed word from puzzle 1: " + movedStacks.getTopFromEachColumn())
}

/**
 * Puzzle 2: get top from column with reversed pile order
 */
fun getPuzzle2TopStackFromEachColumn(
    commands: List<List<Int>>,
    arrayStacks: MutableList<MutableList<String>>
) {
    val movedStacks = moveStacksAccordingToCommands(
        commands,
        arrayStacks,
        shouldChangePileOrder = true
    )

    println("Formed word from puzzle 2: " + movedStacks.getTopFromEachColumn())
}

fun List<List<String>>.getTopFromEachColumn(): String {
    var word = ""
    forEach { column ->
        word += column.first()
    }
    return word
}

fun moveStacksAccordingToCommands(
    commands: List<List<Int>>,
    arrayStacks: MutableList<MutableList<String>>,
    shouldChangePileOrder: Boolean
): MutableList<MutableList<String>> {

    commands.forEach { command ->
        val quantity = command[0]
        val from = command[1] - 1
        val to = command[2] - 1

        val removedPart = arrayStacks[from].subList(0, quantity)
        val keptPart = arrayStacks[from].subList(quantity, arrayStacks[from].size)
        val stackToMove = if (shouldChangePileOrder) removedPart else removedPart.reversed()

        arrayStacks[to].addAll(0, stackToMove)
        arrayStacks[from] = keptPart
    }

    return arrayStacks
}

fun getSupplyStacks(
    inputStacks: List<String>,
    indexReferenceForInputSplit: Int
): MutableList<MutableList<String>> {
    val fullSupplyStack = inputStacks.subList(0, indexReferenceForInputSplit).toMutableList()
    val sizeForArray = fullSupplyStack.removeLast().toSet().getSizeFromDigitElements()
    val supplyStackArray = MutableList<MutableList<String>>(sizeForArray) {
        mutableListOf()
    }

    fullSupplyStack.forEach { topFromStacks ->
        val singleStack = topFromStacks.chunked(4)
        singleStack.forEachIndexed { index, stack ->
            supplyStackArray[index].add(stack)
        }
    }

    return supplyStackArray.removeUnnecessaryCharsFromStacks()
}

fun MutableList<MutableList<String>>.removeUnnecessaryCharsFromStacks() = map { stack ->
    stack.asSequence()
        .map(String::removeBracketsAndSpaces)
        .mapNotNull(String::replaceEmptyForNull)
        .toMutableList()
}.toMutableList()

fun String.removeBracketsAndSpaces(): String {
    return replace(oldValue = " ", newValue = "")
        .replace(oldValue = "[", newValue = "")
        .replace(oldValue = "]", newValue = "")
}

fun String.replaceEmptyForNull() = ifEmpty { return@ifEmpty null }

fun Set<Char>.getSizeFromDigitElements() = filter(Char::isDigit).size

fun getCommandsForStackMoves(
    inputStacks: List<String>,
    indexReferenceForInputSplit: Int
): List<List<Int>> {
    val commandsInput = inputStacks.subList(indexReferenceForInputSplit + 1, inputStacks.size)
    return commandsInput.removeUnnecessaryWordsFromCommands()
}

fun List<String>.removeUnnecessaryWordsFromCommands(): List<List<Int>> = map(String::removeWords)
    .map(String::splitBySpaces)
    .map { commands ->
        commands.toList().mapNotNull { command ->
            command.replaceEmptyForNull()?.toInt()
        }
    }

fun String.removeWords(): String {
    return replace(oldValue = "move", newValue = "")
        .replace(oldValue = "from", newValue = "")
        .replace(oldValue = "to", newValue = "")
}

fun String.splitBySpaces(): List<String> {
    return split(" ")
}
