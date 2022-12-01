/**
 * Advent of Code 2022
 * Day 1 / Puzzle 1
 * @author tmikulsk1
 *
 * 1st read input values
 * 2nd separate calories carried per elf
 * 3rd separate and convert calories per elf
 * 4th sum total amount of calories per elf
 * 5th print answer
 */

const val INPUT_FILE_NAME = "day-01/puzzle1/input.txt"

fun main() {

    /* 1st read input values */
    val elvesCaloriesInput: String = readInput(INPUT_FILE_NAME).readText()

    /* 2nd separate calories carried per elf */
    val elvesCalories = elvesCaloriesInput.split("\n\n")

    /* 3rd separate and convert calories per elf [and] 4th sum total amount of calories per elf */
    val calories: List<Int> = elvesCalories.map { calorie ->
        return@map calorie.split("\n").map(String::toInt)
    }.map(List<Int>::sum)

    val maxCalorie = calories.max()
    val positionOfMaxCalorie = calories.indexOf(maxCalorie) + 1

    /* 5th print answer */
    println("Maximum calorie carried: $maxCalorie")
    println("Carried by elf in position $positionOfMaxCalorie")
}
