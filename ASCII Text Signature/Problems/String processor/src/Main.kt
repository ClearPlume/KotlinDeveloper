import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val str1 = scanner.nextLine()
    val operator = scanner.nextLine()
    val str2 = scanner.nextLine()

    println(when (operator) {
        "equals" -> str1 == str2
        "plus" -> str1.plus(str2)
        "endsWith" -> str1.endsWith(str2)
        else -> "Unknown operation"
    })
}