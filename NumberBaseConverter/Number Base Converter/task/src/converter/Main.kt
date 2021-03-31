package converter

import java.util.*

val HEX_MAPPING = mapOf(10 to "A", 11 to "B", 12 to "C", 13 to "D", 14 to "E", 15 to "F")

fun main() {
    print("Enter number in decimal system:")
    var original = Scanner(System.`in`).nextInt()

    print("Enter target base:")
    val base = Scanner(System.`in`).nextInt()

    var result = ""

    do {
        val remainder = original % base
        result = (if (remainder > 9) HEX_MAPPING[remainder] else remainder).toString() + result
        original /= base
    } while (original > 0)

    print("Conversion result: $result")
}