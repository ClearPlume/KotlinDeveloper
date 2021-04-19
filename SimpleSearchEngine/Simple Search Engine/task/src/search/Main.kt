package search

import java.io.File
import java.util.*
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    val persons = mutableListOf<String>()
    File(args[1]).forEachLine { persons.add(it) }

    while (true) {
        when (menu()) {
            1 -> {
                println("Enter a name or email to search all suitable people.")
                val keyword = scanner.nextLine()

                val found = mutableListOf<String>()
                for (line in persons) {
                    if (keyword.toLowerCase() in line.toLowerCase()) {
                        found.add(line)
                    }
                }

                if (found.isEmpty()) {
                    println("No matching people found.")
                } else {
                    for (s in found) {
                        println(s)
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