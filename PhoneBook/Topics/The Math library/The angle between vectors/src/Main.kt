import java.util.*
import kotlin.math.acos
import kotlin.math.hypot

fun main() {
    // put your code here
    val scanner = Scanner(System.`in`)
    val (x1, y1, x2, y2) = Array(4) { scanner.nextDouble() }

    val dot = x1 * x2 + y1 * y2
    val mod = hypot(x1, y1) * hypot(x2, y2)

    println((acos(dot / mod) / 3.14 * 180).toInt())
}