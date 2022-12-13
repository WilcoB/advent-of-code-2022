package solutions

import getFile
import java.util.*

class Day05 {
    fun execute() {
        executePartOne()
        executePartTwo()
    }

    private fun executePartOne() {
        println("After the arrangement procedures (part 1), the top crates are ${solve(retainOrder = false)}")

    }

    private fun executePartTwo() {
        println("After the arrangement procedures (part 2), the top crates are ${solve(retainOrder = true)}")
    }

    private fun solve(retainOrder: Boolean): String {
        val inputParts = getFile("input-day05")
            .readText()
            .split("\n\n")

        val rows = mutableMapOf<Int, Stack<String>>()
            .setup(input = inputParts[0])
            .executeProcedures(input = inputParts[1], retainOrder = retainOrder)

        return rows.toSortedMap()
            .map {
                it.value.pop()
            }
            .reduce { acc, crate ->
                acc + crate
            }
    }

    private fun MutableMap<Int, Stack<String>>.setup(input: String): MutableMap<Int, Stack<String>> {
        input.lines()
            .forEach { horizontalRow ->
                CRATE_REGEX.findAll(horizontalRow).forEach { crate ->
                    val verticalRowNumber = (crate.range.first / 4) + 1
                    val row = this.getOrPut(verticalRowNumber) {
                        Stack<String>()
                    }
                    row.push(crate.groupValues[2])
                }
            }

        this.map { it.key to it.value.reverse() }
        return this
    }

    private fun MutableMap<Int, Stack<String>>.executeProcedures(
        input: String,
        retainOrder: Boolean
    ): MutableMap<Int, Stack<String>> {
        PROCEDURE_REGEX.findAll(input).forEach { matchResult ->
            val cratesToBeMoved = matchResult.groupValues[1].toInt()
            val fromRow = matchResult.groupValues[2].toInt()
            val toRow = matchResult.groupValues[3].toInt()

            if (retainOrder) {
                val stackToBeMoved = Stack<String>()
                repeat(cratesToBeMoved) {
                    stackToBeMoved.add(this[fromRow]?.pop())
                }

                stackToBeMoved.reversed()
                    .forEach {
                        this[toRow]?.add(it)
                    }
            } else {
                repeat(cratesToBeMoved) {
                    this[toRow]?.add(this[fromRow]?.pop())
                }
            }
        }

        return this
    }

    companion object {
        val CRATE_REGEX = """(\[([A-Z])\])""".toRegex()
        val PROCEDURE_REGEX = """move ([\d]+) from ([\d]+) to ([\d]+)""".toRegex()
    }
}