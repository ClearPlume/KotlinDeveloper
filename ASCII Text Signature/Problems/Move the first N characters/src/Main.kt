import java.util.*

fun main() {
    val string = Scanner(System.`in`).nextLine()

    val sArr = string.split(' ').toTypedArray()
    var s = sArr[0]
    val n = sArr[1].toInt()
    var firstN = ""

    if (n < s.length) {
        for (i in 0 until n) {
            firstN += s[i]
        }

        s = s.replaceFirst(firstN, "")
    }

    print(s + firstN)
}