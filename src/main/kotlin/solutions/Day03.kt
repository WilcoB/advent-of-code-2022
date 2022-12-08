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
        val rucksacks = getFile("input-day03").readLines()
        val groups = mutableListOf<List<String>>()

        for (i in rucksacks.indices step 3) {
            groups.add(listOf(rucksacks[i], rucksacks[i + 1], rucksacks[i + 2]))
        }

        val prioritySum = groups.sumOf {
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