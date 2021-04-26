import java.util.*
import kotlin.math.pow
import kotlin.math.sqrt

fun main() {
    // put your code here
    val scanner = Scanner(System.`in`)
    val (a, b, c) = Array(3) { scanner.nextDouble() }

    val delta = b.pow(2) - 4 * a * c
    val x1 = (-b + sqrt(delta)).div(2 * a)
    val x2 = (-b - sqrt(delta)).div(2 * a)

    println(if (x1 > x2) "$x2 $x1" else "$x1 $x2")
}