import java.util.*

fun main() {
    val input = Scanner(System.`in`)

    val ticketNumber = input.next()
    val first = ticketNumber.substring(0, 3)
    val last = ticketNumber.substring(3)

    print(if (calcSumOfDigits(first) == calcSumOfDigits(last)) "Lucky" else "Regular")
}

fun calcSumOfDigits(digits: String): Int {
    var sum = 0

    for (c in digits) {
        sum += Character.getNumericValue(c)
    }

    return sum
}