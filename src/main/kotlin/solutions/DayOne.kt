package solutions

import java.io.File

class DayOne {
    fun execute() {
        executePartOne()
        executePartTwo()
    }

    private fun executePartOne() {
        println("The Elf with the most calories carries ${getCaloriesPerElf().max()} calories")
    }

    private fun executePartTwo() {
        val topThreeTotalCalories = getCaloriesPerElf()
            .sortedDescending()
            .take(3)
            .sum()

        println("The top three Elves are carrying $topThreeTotalCalories calories in total")
    }

    private fun getCaloriesPerElf(): List<Int> {
        val input = File("src/main/resources/raw/input-day-one.txt").readText()
        return input.split("\n\n").map {
            it.split("\n").fold(0) { acc, calories ->
                acc + calories.toInt()
            }
        }
    }
}