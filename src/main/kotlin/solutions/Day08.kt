package solutions

import getFile

class Day08 {
    fun execute() {
        executePartOne()
        executePartTwo()
    }

    private fun executePartOne() {
        val grid = getFile("input-day08")
            .readLines()
            .map { it.toCharArray() }

        val visibleTrees = grid.sumOf { row ->
            val y = grid.indexOf(row)
            var x = -1

            row.count {
                x++
                grid.isTreeVisible(x, y)
            }
        }

        println("$visibleTrees trees are visible from outside the grid")
    }

    private fun executePartTwo() {
        val grid = getFile("input-day08")
            .readLines()
            .map { it.toCharArray() }

        val highestScore = grid.maxOf { horizontalTrees ->
            val y = grid.indexOf(horizontalTrees)
            var x = -1

            horizontalTrees.maxOf {
                x++
                grid.getTreeScenicScore(x, y)
            }
        }

        println("$highestScore is the highest scenic score possible")
    }

    private fun List<CharArray>.isTreeVisible(x: Int, y: Int): Boolean {
        val onEdge = (x - 1 < 0 || x + 1 == this.size || y - 1 < 0 || y + 1 == this.size)
        val visibleFromLeft = this.getLeftTrees(x, y).all { it < this[y][x] }
        val visibleFromRight = this.getRightTrees(x, y).all { it < this[y][x] }
        val visibleFromTop = this.getTopTrees(x, y).all { it < this[y][x] }
        val visibleFromBottom = this.getBottomTrees(x, y).all { it < this[y][x] }

        return onEdge || visibleFromLeft || visibleFromRight || visibleFromTop || visibleFromBottom
    }

    private fun List<CharArray>.getTreeScenicScore(x: Int, y: Int): Int {
        val visibleLeftTrees = this.getLeftTrees(x, y)
            .reversedArray()
            .filterVisible(tree = this[y][x])
            .count { it.isDigit() }

        val visibleRightTrees = this.getRightTrees(x, y)
            .filterVisible(tree = this[y][x])
            .count { it.isDigit() }

        val visibleTopTrees = this.getTopTrees(x, y)
            .reversedArray()
            .filterVisible(tree = this[y][x])
            .count { it.isDigit() }

        val visibleBottomTrees = this.getBottomTrees(x, y)
            .filterVisible(tree = this[y][x])
            .count { it.isDigit() }

        return visibleLeftTrees * visibleRightTrees * visibleTopTrees * visibleBottomTrees
    }

    private fun CharArray.filterVisible(tree: Char): CharArray {
        return this.firstOrNull { it >= tree }?.let { lastVisibleTree ->
            this.sliceArray(0..this.indexOf(lastVisibleTree))
        } ?: this
    }

    private fun List<CharArray>.getLeftTrees(x: Int, y: Int): CharArray = this[y].sliceArray(0 until x)

    private fun List<CharArray>.getRightTrees(x: Int, y: Int): CharArray = this[y].sliceArray(x + 1 until this.size)

    private fun List<CharArray>.getTopTrees(x: Int, y: Int): CharArray = CharArray(this.size).apply {
        for (yToCheck in 0 until y) {
            this[yToCheck] = this@getTopTrees[yToCheck][x]
        }
    }

    private fun List<CharArray>.getBottomTrees(x: Int, y: Int): CharArray = CharArray(this.size).apply {
        for (yToCheck in y + 1 until this@getBottomTrees.size) {
            this[yToCheck] = this@getBottomTrees[yToCheck][x]
        }
    }
}