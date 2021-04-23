import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

fun main() {
    // write your code here
    val scanner = Scanner(System.`in`)
    val (a, b, c) = Array(3) { BigDecimal(scanner.next()) }

    print((a + b + c).div(BigDecimal.valueOf(3)).setScale(0, RoundingMode.DOWN))
}