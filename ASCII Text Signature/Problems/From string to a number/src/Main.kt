import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    println(when (scanner.next()) {
        "one" -> 1
        "two" -> 2
        "three" -> 3
        "four" -> 4
        "five" -> 5
        "six" -> 6
        "seven" -> 7
        "eight" -> 8
        "nine" -> 9
        else -> "error!"
    })
}