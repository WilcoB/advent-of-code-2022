package solutions

import getFile

class Day02 {
    fun execute() {
        executePartOne()
        executePartTwo()
    }

    private fun executePartOne() {
        val score = getFile("input-day02")
            .readLines()
            .sumOf {
                val moves = it.split(" ")
                calculateScore(own = moves[1], opponent = moves[0])
            }

        println("The total score of part one is: $score")
    }

    private fun executePartTwo() {
        val score = getFile("input-day02")
            .readLines()
            .sumOf {
                val moves = it.split(" ")
                val own = when (moves[1]) {
                    OUTCOME_LOSE -> when (moves[0]) {
                        OPPONENT_ROCK -> OWN_SCISSORS
                        OPPONENT_PAPER -> OWN_ROCK
                        OPPONENT_SCISSORS -> OWN_PAPER
                        else -> null
                    }

                    OUTCOME_DRAW -> when (moves[0]) {
                        OPPONENT_ROCK -> OWN_ROCK
                        OPPONENT_PAPER -> OWN_PAPER
                        OPPONENT_SCISSORS -> OWN_SCISSORS
                        else -> null
                    }

                    OUTCOME_WIN -> when (moves[0]) {
                        OPPONENT_ROCK -> OWN_PAPER
                        OPPONENT_PAPER -> OWN_SCISSORS
                        OPPONENT_SCISSORS -> OWN_ROCK
                        else -> null
                    }

                    else -> null
                }
                calculateScore(own = own, opponent = moves[0])
            }

        println("The total score of part two is: $score")
    }
    
    private fun calculateScore(own: String?, opponent: String): Int {
        return when (own) {
            OWN_ROCK -> 1 + when (opponent) {
                OPPONENT_SCISSORS -> 6
                OPPONENT_ROCK -> 3
                else -> 0
            }

            OWN_PAPER -> 2 + when (opponent) {
                OPPONENT_ROCK -> 6
                OPPONENT_PAPER -> 3
                else -> 0
            }

            OWN_SCISSORS -> 3 + when (opponent) {
                OPPONENT_PAPER -> 6
                OPPONENT_SCISSORS -> 3
                else -> 0
            }

            else -> 0
        }
    }

    companion object {
        const val OPPONENT_ROCK = "A"
        const val OPPONENT_PAPER = "B"
        const val OPPONENT_SCISSORS = "C"

        const val OWN_ROCK = "X"
        const val OWN_PAPER = "Y"
        const val OWN_SCISSORS = "Z"

        const val OUTCOME_LOSE = "X"
        const val OUTCOME_DRAW = "Y"
        const val OUTCOME_WIN = "Z"
    }
}