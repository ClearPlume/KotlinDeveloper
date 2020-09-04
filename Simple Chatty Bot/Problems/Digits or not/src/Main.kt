import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val c1 = scanner.next()[0].isDigit()
    val c2 = scanner.next()[0].isDigit()
    val c3 = scanner.next()[0].isDigit()
    val c4 = scanner.next()[0].isDigit()

    println("$c1\\$c2\\$c3\\$c4")
}