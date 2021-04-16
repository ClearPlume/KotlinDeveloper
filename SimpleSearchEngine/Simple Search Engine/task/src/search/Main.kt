package search

import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)

    val words = scanner.nextLine().split(' ')
    val keywords = scanner.next()

    val index = words.indexOf(keywords)
    println(if (index != -1) index + 1 else "Not found")
}
