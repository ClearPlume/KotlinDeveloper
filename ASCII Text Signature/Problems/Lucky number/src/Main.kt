import java.util.*

fun main() {
    val num = Scanner(System.`in`).next()
    val i = num.length / 2
    val first = num.substring(0, i)
    val last = num.substring(i)

    print(if (calcSumOfDigits(first) == calcSumOfDigits(last)) "YES" else "NO")
}

fun calcSumOfDigits(digits: String): Int {
    var sum = 0

    for (c in digits) {
        sum += Character.getNumericValue(c)
    }

    return sum
}