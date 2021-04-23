import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

fun main() {
    // write your code here
    val scanner = Scanner(System.`in`)
    val (startingAmount, yearlyPercent, years) = Array(3) { BigDecimal(scanner.next()) }
    println(
        "Amount of money in the account: ${
            (startingAmount * (BigDecimal.ONE + yearlyPercent.setScale(2) / BigDecimal.valueOf(100))
                .pow(years.toInt())).setScale(2, RoundingMode.CEILING)
        }"
    )
}