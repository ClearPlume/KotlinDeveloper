package calculator

import java.util.*
import kotlin.system.exitProcess

fun main() {
    while (true) {
        when (val input = Scanner(System.`in`).nextLine()) {
            "/exit" -> {
                println("Bye!")
                exitProcess(0)
            }
            "" -> continue
            else -> println(input.split(" ").sumBy { it.toInt() })
        }
    }
}
