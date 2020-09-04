import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val num = scanner.nextInt()

    println(if (num % 2 == 0) "EVEN" else "ODD")
}