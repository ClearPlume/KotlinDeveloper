import java.util.*
import kotlin.math.pow

fun main() {
    // put your code here
    val scanner = Scanner(System.`in`)
    val (a, b) = Array(2) { scanner.nextDouble() }
    println(a.pow(b))
}