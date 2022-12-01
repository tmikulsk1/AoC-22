/**
 * Advent of Code 2022
 * Day 1 / Puzzle 1
 * @author tmikulsk1
 *
 * 1st read input values
 * 2nd separate calories carried per elf
 * 3rd separate and convert calories per elf
 * p1: sum total amount of calories per elf
 * p2: show top three calories sum
 * final: print answers
 */

const val INPUT_FILE_NAME = "day-01/input.txt"

fun main() {

    val caloriesPerElf = getCaloriesPerElv()

    /* puzzle 1 answer */
    val maxCalorie = getMaximumCalorieCarried(caloriesPerElf)

    /* puzzle 2 answer */
    val topThreeCaloriesSum = caloriesPerElf.getTotalCaloriesCarriedByElves(untilValue = 3)

    /* final: print answers */
    println("Maximum calorie carried: $maxCalorie")
    println("Top three calories sum: $topThreeCaloriesSum")
}

/* p1: sum total amount of calories per elf*/
fun getMaximumCalorieCarried(calories: List<Int>) = calories.max()

/* p2: show top three calories sum */
fun List<Int>.getTotalCaloriesCarriedByElves(untilValue: Int) =
    sortedDescending().subList(0, untilValue).sum()

fun getCaloriesPerElv(): List<Int> {
    /* 1st read input values */
    val elvesCaloriesInput: String = readInput(INPUT_FILE_NAME).readText()

    /* 2nd separate calories carried per elf */
    val elvesCalories = elvesCaloriesInput.split("\n\n")

    /* 3rd separate and convert calories per elf [and] 4th sum total amount of calories per elf */
    return elvesCalories.map { calorie ->
        return@map calorie.split("\n").map(String::toInt)
    }.map(List<Int>::sum)
}
