import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val a = scanner.nextInt()
    val b = scanner.nextInt()
    val h = scanner.nextInt()

    println(if (h in a..b) "Normal" else if (h < a) "Deficiency" else "Excess")
}