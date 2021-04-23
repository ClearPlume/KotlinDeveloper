import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val c1 = scanner.next()[0]
    val c2 = scanner.next()[0]
    val c3 = scanner.next()[0]

    println(c2 == c1 + 1 && c3 == c2 + 1)
}