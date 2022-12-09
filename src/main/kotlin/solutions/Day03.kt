package solutions

import getFile

class Day03 {
    fun execute() {
        executePartOne()
        executePartTwo()
    }

    private fun executePartOne() {
        val prioritySum = getFile("input-day03")
            .readLines()
            .sumOf {
                val compartmentOne = it.substring(startIndex = 0, endIndex = it.length / 2)
                val compartmentTwo = it.substring(startIndex = it.length / 2)
                val matchingItem = compartmentOne.first { item ->
                    compartmentTwo.contains(item)
                }
                getItemPriority(matchingItem)
            }

        println("The sum of the priorities of the item types is: $prioritySum (part 1)")
    }

    private fun executePartTwo() {
        val prioritySum = getFile("input-day03")
            .readLines()
            .chunked(3)
            .sumOf {
                val badgeItem = it[0].first { item ->
                    it[1].contains(item) && it[2].contains(item)
                }
                getItemPriority(badgeItem)
            }

        println("The sum of the priorities of the item types is: $prioritySum (part 2)")
    }

    private fun getItemPriority(item: Char): Int {
        return if (item.isUpperCase()) item.code - 38 else item.code - 96
    }
}