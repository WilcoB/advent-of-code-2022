package solutions

import getFile
import java.util.*

class Day06 {
    fun execute() {
        executePartOne()
        executePartTwo()
    }

    private fun executePartOne() {
        println("${getProcessedCharacters(4)} characters need to be processed before the first start-of-packet marker is detected")
    }

    private fun executePartTwo() {
        println("${getProcessedCharacters(14)} characters need to be processed before the first start-of-message marker is detected")
    }

    private fun getProcessedCharacters(markerSize: Int): Int {
        val buffer = Buffer()
        for (char in getFile("input-day06").readText()) {
            if (buffer.addCharAndCheckMarker(character = char, markerSize = markerSize)) {
                break
            }
        }

        return buffer.currentIndex
    }

    data class Buffer(var currentIndex: Int = 0, val characters: LinkedList<Char> = LinkedList<Char>()) {
        fun addCharAndCheckMarker(character: Char, markerSize: Int): Boolean {
            if (characters.size == markerSize) {
                characters.pop()
            }

            currentIndex++
            characters.add(character)

            return characters.size == markerSize && characters.all { char ->
                characters.fold("") { acc, c -> acc + c }.count { it == char } == 1
            }
        }
    }
}