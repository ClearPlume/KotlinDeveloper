package search

import java.io.File
import java.util.*
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    val persons = mutableListOf<String>()
    File(args[1]).forEachLine { persons.add(it) }
    val invertedIndex = persons.init()

    while (true) {
        when (menu()) {
            1 -> {
                println("Enter a name or email to search all suitable people.")
                val keyword = scanner.nextLine()

                val found = invertedIndex[keyword] ?: mutableListOf()

                if (found.isEmpty()) {
                    println("No matching people found.")
                } else {
                    for (lineIndex in found) {
                        println(persons[lineIndex])
                    }
                }
            }
            2 -> {
                println("=== List of people ===")
                for (line in persons) {
                    println(line)
                }
            }
            0 -> {
                println("Bye!")
                exitProcess(0)
            }
        }
    }
}

private fun MutableList<String>.init(): Map<String, MutableList<Int>> {
    val indices = mutableMapOf<String, MutableList<Int>>()

    forEachIndexed { index, line ->
        for (word in line.split(Regex("\\s+"))) {
            if (word in indices) {
                indices[word]!!.add(index)
            } else {
                indices[word] = mutableListOf(index)
            }
        }
    }

    return indices.toMap()
}

fun menu(): Int {
    println(
        """
        === Menu ===
        1. Find a person
        2. Print all people
        0. Exit
    """.trimIndent()
    )

    var choose: Int

    do {
        choose = Scanner(System.`in`).nextInt()

        if (choose !in 0..2) {
            println("Incorrect option! Try again.")
            choose = -1
        }
    } while (choose == -1)

    return choose
}