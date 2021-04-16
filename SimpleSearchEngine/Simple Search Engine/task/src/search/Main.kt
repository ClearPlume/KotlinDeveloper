package search

import java.util.*
import kotlin.system.exitProcess

fun main() {
    val scanner = Scanner(System.`in`)

    println("Enter the number of people:")
    val num = scanner.nextInt()

    println("Enter all people:")
    val lines = Array(num) { readLine()!! }

    while (true) {
        when (menu()) {
            1 -> {
                println("Enter a name or email to search all suitable people.")
                val keyword = scanner.next()

                val found = MutableList(0) { "" }
                for (line in lines) {
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
                for (line in lines) {
                    println(line)
                }
            }
            0 -> exitProcess(0)
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