package solutions

import getFile
import kotlin.math.abs

class Day09 {
    fun execute() {
        executePartOne()
        executePartTwo()
    }

    private fun executePartOne() {
        println("At ${getTailVisits(ropeSize = 2)} positions the tail of the rope visits at least once (part 1)")
    }

    private fun executePartTwo() {
        println("At ${getTailVisits(ropeSize = 10)} positions the tail of the rope visits at least once (part 1)")
    }

    private fun getTailVisits(ropeSize: Int): Int {
        val knots = MutableList(ropeSize) { Position(0, 0) }
        val tailVisits = mutableListOf<Position>()

        getFile("input-day09")
            .readLines()
            .forEach {
                val motion = it.split(" ")
                val direction = motion[0]
                val steps = motion[1].toInt()

                var changeX = 0
                var changeY = 0

                when (direction) {
                    "U" -> changeY = 1
                    "D" -> changeY = -1
                    "L" -> changeX = -1
                    "R" -> changeX = 1
                }

                repeat(steps) {
                    // Move head:
                    knots[0] = Position(x = knots[0].x + changeX, y = knots[0].y + changeY)

                    // Move tails:
                    for (i in 1 until knots.size) {
                        val previousKnot = knots[i - 1]
                        val previousKnotAbove = previousKnot.y > knots[i].y
                        val previousKnotRight = previousKnot.x > knots[i].x

                        val deltaX = abs(previousKnot.x - knots[i].x)
                        val deltaY = abs(previousKnot.y - knots[i].y)

                        val newPosition = if (deltaX <= 1 && deltaY <= 1) {
                            // Previous knot is within 1 radius, don't move current:
                            knots[i]
                        } else {
                            // Get the base changes, based on the delta values:
                            var changeTailX = deltaX - 1
                            var changeTailY = deltaY - 1

                            // If delta Y is greater than X add 1 to X (or vice versa), so it stays behind the previous knot:
                            if (deltaY > deltaX) {
                                changeTailX++
                            } else if (deltaX > deltaY) {
                                changeTailY++
                            }

                            // Check the direction, multiply by -1 if going left or down:
                            changeTailX *= if (previousKnotRight) 1 else -1
                            changeTailY *= if (previousKnotAbove) 1 else -1

                            Position(x = knots[i].x + changeTailX, y = knots[i].y + changeTailY)
                        }

                        knots[i] = newPosition
                    }

                    tailVisits.addIfNotExists(knots.last())
                }
            }

        return tailVisits.size
    }

    private fun MutableList<Position>.addIfNotExists(position: Position) {
        if (this.none { p -> p.x == position.x && p.y == position.y }) {
            this.add(position)
        }
    }

    data class Position(var x: Int = 0, var y: Int = 0)
}