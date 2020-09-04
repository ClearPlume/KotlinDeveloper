import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val a = scanner.nextInt()
    val b = scanner.nextInt()

    if (a > b || a == b) {
        println(a)
    }

    if (a < b) {
        println(b)
    }
}