import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    var curPos = 1
    var maxPos = curPos
    var maxNum = Int.MIN_VALUE

    while (scanner.hasNextInt()) {
        val nextInt = scanner.nextInt()
        if (maxNum < nextInt) {
            maxNum = nextInt
            maxPos = curPos
        }
        curPos++
    }

    print("$maxNum $maxPos")
}