import java.util.*
import kotlin.math.max

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val numberOfNum = scanner.nextInt()
    var firstNum = scanner.nextInt()
    var longestSequence = 1
    var longestSequenceTmp = longestSequence

    for (i in 1 until numberOfNum) {
        val nextInt = scanner.nextInt()
        if (firstNum <= nextInt) {
            longestSequenceTmp++

            if (i == numberOfNum - 1) {
                longestSequence = longestSequenceTmp
            }
        } else {
            longestSequence = max(longestSequence, longestSequenceTmp)
            longestSequenceTmp = 1
        }
        firstNum = nextInt
    }

    println(longestSequence)
}