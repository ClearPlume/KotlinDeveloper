import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val input = scanner.nextLine().split(" ")
    val num1 = input[0].toLong()
    val operator = input[1][0]
    val num2 = input[2].toLong()

    println(when (operator) {
        '+' -> num1 + num2
        '-' -> num1 - num2
        '*' -> num1 * num2
        '/' -> if (num2 == 0L) "Division by 0!" else num1 / num2
        else -> "Unknown operator"
    })
}