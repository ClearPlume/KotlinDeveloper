package converter

import java.math.BigInteger
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
            val source = (scanner.next())

            if (source == "/back") {
                break
            }

            if (srcBase == tarBase) {
                println("Conversion result: $source")
                continue
            }

            var decimal = BigInteger.ZERO
            var curDigitNum = source.lastIndex

            for (digit in source) {
                decimal += srcBase.pow(curDigitNum--) * digitChars.indexOf(digit).toBigInteger()
            }

            if (tarBase.toInt() == 10) {
                println("Conversion result: $decimal")
                continue
            }

            var result = ""

            do {
                val remainder = decimal % tarBase
                result += digitChars[remainder.toInt()]
                decimal /= tarBase
            } while (decimal > BigInteger.ZERO)

            println("Conversion result: ${result.reversed()}")
        } while (true)
    }
}