import java.math.BigDecimal
import java.math.RoundingMode

fun main() {
    // write your code here
    val decimal = BigDecimal(readLine()!!)
    val scale = readLine()!!.toInt()
    println(decimal.setScale(scale, RoundingMode.HALF_DOWN))
}