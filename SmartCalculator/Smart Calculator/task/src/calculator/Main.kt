package calculator

import java.util.*

fun main() {
    while (true) {
        when (val input = Scanner(System.`in`).nextLine().trim()) {
            "/exit" -> break
            "" -> continue
            "/help" -> println("The program calculates the sum of numbers")
            else -> println(input.split(" ").sumBy { it.toInt() })
        }
    }
    println("Bye!")
}
