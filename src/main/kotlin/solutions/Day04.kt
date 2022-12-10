package solutions

import getFile

class Day04 {
    fun execute() {
        executePartOne()
        executePartTwo()
    }

    private fun executePartOne() {
        println("In ${getNumberOfOverlap(true)} assignments, one range fully contains the other")
    }

    private fun executePartTwo() {
        println("In ${getNumberOfOverlap(false)} assignments, one range partially contains the other")
    }

    private fun getNumberOfOverlap(fullOverlap: Boolean): Int {
        return getFile("input-day04")
            .readLines()
            .count { group ->
                val ranges = group
                    .split(",")
                    .map {
                        it.split("-")[0].toInt() .. it.split("-")[1].toInt()
                    }

                if (fullOverlap) {
                    ranges[0].all { ranges[1].contains(it) } || ranges[1].all { ranges[0].contains(it) }
                } else {
                    ranges[0].any { ranges[1].contains(it) } || ranges[1].any { ranges[0].contains(it) }
                }
            }
    }
}