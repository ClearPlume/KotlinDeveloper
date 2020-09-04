import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val num = scanner.nextInt()

    println(if (num > 0) "positive" else if (num < 0) "negative" else "zero")
}