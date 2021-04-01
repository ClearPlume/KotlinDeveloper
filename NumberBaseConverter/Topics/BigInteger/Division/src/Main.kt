import java.math.BigDecimal
import java.util.*

fun main() {
    // write your code here
    val scanner = Scanner(System.`in`)
    val (a, b) = Array(2) { BigDecimal(scanner.next()) }
    val (quotient, remainder) = a.divideAndRemainder(b)

    println("$a = $b*$quotient + $remainder")
}