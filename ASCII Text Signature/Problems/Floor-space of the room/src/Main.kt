import java.util.*
import kotlin.math.sqrt

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    when (scanner.next()) {
        "triangle" -> {
            val a = scanner.nextDouble()
            val b = scanner.nextDouble()
            val c = scanner.nextDouble()

            val s = (a + b + c) / 2

            println(sqrt(s * (s - a) * (s - b) * (s - c)))
        }

        "rectangle" -> {
            val a = scanner.nextDouble()
            val b = scanner.nextDouble()

            println(a * b)
        }

        "circle" -> {
            val r = scanner.nextDouble()

            println(3.14 * r * r)
        }
    }
}