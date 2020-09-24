import java.util.*

fun main() {
    val string = Scanner(System.`in`).next()

    val halfLength = string.length / 2
    val left = string.substring(0, halfLength - if (string.length % 2 == 0) 1 else 0)
    val right = string.substring(halfLength + 1)

    print(left + right)
}