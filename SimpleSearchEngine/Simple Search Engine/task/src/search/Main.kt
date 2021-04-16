package search

import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)

    println("Enter the number of people:")
    val num = scanner.nextInt()

    println("Enter all people:")
    val lines = Array(num) { readLine()!! }
    println()

    println("Enter the number of search queries:")

    repeat(scanner.nextInt()) {
        println("Enter data to search people:")
        val keyword = scanner.next()
        println()

        val found = MutableList(0) { "" }
        for (line in lines) {
            if (keyword.toLowerCase() in line.toLowerCase()) {
                found.add(line)
            }
        }

        println("Found people:")
        if (found.isEmpty()) {
            println("No matching people found.")
        } else {
            for (s in found) {
                println(s)
            }
        }
        println()
    }
}
