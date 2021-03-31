package converter

import java.util.*
import kotlin.math.pow

val DEC_HEX_MAPPING = mapOf(10 to "A", 11 to "B", 12 to "C", 13 to "D", 14 to "E", 15 to "F")
val HEX_DEC_MAPPING = mapOf('a' to 10, 'b' to 11, 'c' to 12, 'd' to 13, 'e' to 14, 'f' to 15)

fun main() {
    val scanner = Scanner(System.`in`)

    do {
        print("Do you want to convert /from decimal or /to decimal? (To quit type /exit)")
        val command = scanner.next()

        when (command) {
            "/from" -> {
                print("Enter number in decimal system:")
                var decimal = Scanner(System.`in`).nextInt()

                print("Enter target base:")
                val tarBase = Scanner(System.`in`).nextInt()

                var result = ""

                do {
                    val remainder = decimal % tarBase
                    result = (if (remainder > 9) DEC_HEX_MAPPING[remainder] else remainder).toString() + result
                    decimal /= tarBase
                } while (decimal > 0)

                println("Conversion to decimal result: $result")
            }
            "/to" -> {
                print("Enter source number:")
                val source = scanner.next()

                print("Enter source base:")
                val srcBase = scanner.nextInt()

                var result = 0
                var curDigitNum = source.lastIndex

                for (digit in source) {
                    result += if (digit.isDigit()) {
                        srcBase.toDouble().pow(curDigitNum--).toInt() * (digit.toInt() - 48)
                    } else {
                        srcBase.toDouble().pow(curDigitNum--).toInt() * HEX_DEC_MAPPING[digit]!!
                    }
                }

                println("Conversion to decimal result: $result")
            }
            else -> continue
        }

    } while (command != "/exit")
}