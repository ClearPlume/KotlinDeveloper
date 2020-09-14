import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val density = scanner.nextDouble()
    val height = scanner.nextDouble()

    println(density * 9.8 * height)
}