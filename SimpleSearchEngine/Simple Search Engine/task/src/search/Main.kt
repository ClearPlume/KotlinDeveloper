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
                println("Select a matching strategy: ALL, ANY, NONE")
                val strategy = Strategy.valueOf(scanner.nextLine())

                println("Enter a name or email to search all suitable people.")
                val keywords = scanner.nextLine().split(' ')

                val found = invertedIndex[keywords, strategy]

                if (found.isEmpty()) {
                    println("No matching people found.")
                } else {
                    println("${found.size} persons found:")
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

private operator fun Map<Int, MutableList<Word>>.get(keywords: List<String>, strategy: Strategy): List<Int> {
    val found = mutableListOf<Int>()
    val words = keywords.map { Word(it) }

    return when (strategy) {
        Strategy.ALL -> found.also {
            for (lineEntry in this) {
                if (lineEntry.value.containsAll(words)) {
                    it.add(lineEntry.key)
                }
            }
        }

        Strategy.ANY -> found.also {
            for (lineEntry in this) {
                if (lineEntry.value.any { value -> value in words }) {
                    it.add(lineEntry.key)
                }
            }
        }

        Strategy.NONE -> found.also {
            for (lineEntry in this) {
                if (lineEntry.value.all { value -> value !in words }) {
                    it.add(lineEntry.key)
                }
            }
        }
    }
}

private fun MutableList<String>.init(): Map<Int, MutableList<Word>> {
    val indices = mutableListOf<Word>()

    forEachIndexed { index, line ->
        for (word in line.split(Regex("\\s+")).map { Word(it) }) {
            if (word in indices) {
                indices[indices.indexOf(word)].lines.add(index)
            } else {
                word.lines.add(index)
                indices.add(word)
            }
        }
    }

    val lines = mutableMapOf<Int, MutableList<Word>>()

    for (word in indices) {
        for (line in word.lines) {
            if (line in lines) {
                lines[line]!!.add(word)
            } else {
                lines[line] = mutableListOf(word)
            }
        }
    }

    return lines.toMap()
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

enum class Strategy { ALL, ANY, NONE }

data class Word(val word: String, val lines: MutableList<Int> = mutableListOf()) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Word

        if (!word.equals(other.word, true)) return false

        return true
    }

    override fun hashCode(): Int {
        return word.hashCode()
    }
}