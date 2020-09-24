import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    var maxNum = Int.MIN_VALUE
    var curNum = scanner.nextInt()

    while (curNum != 0) {
        maxNum = if (maxNum < curNum) curNum else maxNum
        curNum = scanner.nextInt()
    }

    print(maxNum)
}