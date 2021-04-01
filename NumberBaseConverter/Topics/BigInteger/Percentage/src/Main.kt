import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

fun main() {
    // write your code here
    val scanner = Scanner(System.`in`)
    val (a, b) = Array(2) { BigDecimal(scanner.next()) }

    val aPercentage = a.divide(a.add(b), 2, RoundingMode.FLOOR).toDouble()
    val bPercentage = b.divide(a.add(b), 2, RoundingMode.FLOOR).toDouble()

    println("${(aPercentage * 100).toInt()}% ${(bPercentage * 100).toInt()}%")
}