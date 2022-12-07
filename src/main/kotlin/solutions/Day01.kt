package solutions

import getFile

class Day01 {
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
        return getFile("input-day-one")
            .readText()
            .split("\n\n")
            .map {
                it.split("\n")
                    .fold(0) { acc, calories ->
                        acc + calories.toInt()
                    }
            }
    }
}