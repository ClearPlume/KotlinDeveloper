package sorting

import java.util.*

fun main() {
    // write your code here
    val scanner = Scanner(System.`in`)
    val numbers = mutableListOf<Int>()

    while (scanner.hasNextInt()) {
        numbers.add(scanner.nextInt())
    }

    val total = numbers.size
    val max = numbers.maxOrNull()!!
    val maxNum = numbers.count { it == max }

    println("Total numbers: $total.")
    println("The greatest number: $max ($maxNum time(s)).")
}
