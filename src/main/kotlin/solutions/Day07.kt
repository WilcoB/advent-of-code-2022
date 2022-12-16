package solutions

import getFile
import java.util.*

class Day07 {
    fun execute() {
        executePartOne()
        executePartTwo()
    }

    private fun executePartOne() {
        val sum = getDirectories().values.filter {
            it.size() <= 100000
        }.sumOf {
            it.size()
        }

        println("$sum is the size sum of all directories with 100000 or less size")
    }

    private fun executePartTwo() {
        val directories = getDirectories()
        val size = directories.values.filter {
            it.size() >= (30000000 - (70000000 - directories["/"]!!.size()))
        }.minByOrNull {
            it.size()
        }!!.size()

        println("$size is the size of the directory that needs to be deleted to free up enough space for the update to run")
    }

    private fun getDirectories(): Map<String, Directory> {
        val directories = mutableMapOf<String, Directory>()
        val files = mutableMapOf<String, File>()
        val navigationStack = Stack<Directory>()

        getFile("input-day07")
            .readLines()
            .forEach {
                if (it.startsWith("$")) {
                    val commandParts = it.split(" ")
                    val command = commandParts[1]
                    val parameter = if (commandParts.size == 3) commandParts[2] else null

                    when (command) {
                        "cd" -> {
                            when (parameter) {
                                "/" -> navigationStack.add(directories.getOrPut("/") { Directory(name = "/") })
                                ".." -> navigationStack.pop()
                                else -> {
                                    parameter?.let {
                                        val name = "${navigationStack.peek().name} $parameter"
                                        navigationStack.add(directories.getOrPut(name) { Directory(name = name) })
                                    }
                                }
                            }
                        }
                    }
                } else {
                    val contentParts = it.split(" ")
                    val itemName = "${navigationStack.peek().name} ${contentParts[1]}"

                    if (contentParts[0] == "dir") {
                        val directory = directories.getOrPut(itemName) { Directory(name = itemName) }
                        navigationStack.peek().directories.add(directory)
                    } else {
                        val file = files.getOrPut(itemName) {
                            File(
                                name = itemName,
                                size = contentParts[0].toLong()
                            )
                        }

                        navigationStack.peek().files.add(file)
                    }
                }
            }

        return directories
    }

    data class File(val name: String, val size: Long)

    data class Directory(
        val name: String,
        val directories: MutableList<Directory> = mutableListOf(),
        val files: MutableList<File> = mutableListOf()
    ) {
        fun size(): Long = directories.sumOf { it.size() } + files.sumOf { it.size }
    }
}