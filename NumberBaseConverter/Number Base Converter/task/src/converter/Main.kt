package converter

import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode
import java.util.*
import kotlin.system.exitProcess

fun main() {
    val scanner = Scanner(System.`in`)

    @Suppress("SpellCheckingInspection")
    val digitChars = "0123456789abcdefghijklmnopqrstuvwxyz"

    while (true) {
        print("Enter two numbers in format: {source base} {target base} (To quit type /exit)")
        val command = readLine()!!

        if (command == "/exit") {
            exitProcess(0)
        }

        val (srcBase, tarBase) = command.split(' ').map { BigInteger(it) }

        do {
            print("Enter number in base $srcBase to convert to base $tarBase (To go back type /back)")
            val source = scanner.next()

            // The integer part of number
            val sourceInt = source.substringBefore('.')
            // The fraction part of number
            val sourceFra: String? = if (source.contains('.')) source.substringAfter('.') else null

            if (sourceInt == "/back") {
                break
            }

            // If the source base is equal to the target base, then we don't need any computation
            if (srcBase == tarBase) {
                println("Conversion result: $source")
                continue
            }

            var decimalInt = BigInteger.ZERO
            var curDigitNum = sourceInt.lastIndex

            // Convert number to decimal
            for (digit in sourceInt) {
                decimalInt += srcBase.pow(curDigitNum--) * digitChars.indexOf(digit).toBigInteger()
            }

            var result = ""

            if (tarBase.toInt() == 10) {
                result = decimalInt.toString()
            } else {
                // Convert number to target base
                do {
                    val remainder = decimalInt % tarBase
                    result += digitChars[remainder.toInt()]
                    decimalInt /= tarBase
                } while (decimalInt > BigInteger.ZERO)

                result = result.reversed()
            }

            // If the fraction part of number is null, which means number is an integer, no more fraction need
            if (sourceFra != null) {
                result += '.'

                var decimalFra = BigDecimal.ZERO

                // Convert number's fraction to decimal
                sourceFra.forEachIndexed { index, digit ->
                    decimalFra += digitChars.indexOf(digit).toBigDecimal()
                        .divide(srcBase.toBigDecimal().pow(index + 1), 10, RoundingMode.HALF_UP)
                }

                if (tarBase.toInt() == 10) {
                    result += decimalFra.toString().substringAfter('.')
                } else {
                    // Convert number's fraction to target base
                    do {
                        decimalFra *= tarBase.toBigDecimal()
                        result += digitChars[decimalFra.toInt()]

                        if (decimalFra > BigDecimal.ONE) {
                            decimalFra -= decimalFra.toInt().toBigDecimal()
                        }
                    } while (result.substringAfter('.').length < 5)
                }

                result = result.substring(0, result.indexOf('.') + 6)
            }

            println("Conversion result: $result")
        } while (true)
    }
}