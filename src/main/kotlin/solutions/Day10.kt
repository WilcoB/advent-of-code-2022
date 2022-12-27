package solutions

import getFile
import kotlin.math.abs

class Day10 {
    fun execute() {
        executePartOne()
        executePartTwo()
    }

    private fun executePartOne() {
        println("The sum of the signal strengths is: ${getSumOfSpecifiedCycles(20, 60, 100, 140, 180, 220)}")
    }

    private fun executePartTwo() {
        printCrtAfterAllCycles()
    }

    private fun getSumOfSpecifiedCycles(vararg cycles: Int): Int {
        var x = 1
        var cycle = 0
        var sum = 0

        fun increaseCycleAndAddToSum() {
            cycle++
            if (cycles.contains(cycle)) {
                sum += (cycle * x)
            }
        }

        getFile("input-day10")
            .readLines()
            .forEach {
                if (it == "noop") {
                    increaseCycleAndAddToSum()
                } else {
                    repeat(2) { c ->
                        increaseCycleAndAddToSum()

                        if (c == 1) {
                            x += it.split(" ")[1].toInt()
                        }
                    }
                }
            }


        return sum
    }

    private fun printCrtAfterAllCycles() {
        var y = 0
        var x = 1
        var cycle = 0
        val crt = MutableList(6) { MutableList(40) { '.' } }

        fun increaseCycleAndWriteCharacter() {
            cycle++
            val currentCharacter = cycle - 1 - (y * 40)
            if (abs(x - (currentCharacter)) <= 1) {
                crt[y][currentCharacter] = '#'
            }
        }

        fun increaseY() {
            if (cycle % 40 == 0) {
                y++
            }
        }

        getFile("input-day10")
            .readLines()
            .forEach {
                if (it == "noop") {
                    increaseCycleAndWriteCharacter()
                    increaseY()
                } else {
                    repeat(2) { c ->
                        increaseCycleAndWriteCharacter()

                        if (c == 1) {
                            x += it.split(" ")[1].toInt()
                        }

                        increaseY()
                    }
                }
            }

        // Print CRT screen:
        crt.forEach { row ->
            row.forEach {
                print(it)
            }
            println()
        }
    }
}