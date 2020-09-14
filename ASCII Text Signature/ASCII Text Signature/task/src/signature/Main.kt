package signature

import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)

    val name = scanner.nextLine()

    var border = "**"
    val middle = "* " + name + " *"

    for (i in 1..name.length) {
        border += "*"
    }
    border += "**"

    println(border)
    println(middle)
    println(border)
}