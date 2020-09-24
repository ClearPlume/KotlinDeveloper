import java.util.*

fun main() {
    val string = Scanner(System.`in`).next()
    val left = string.substring(0, string.length / 2)
    val right = string.substring(string.length / 2 + string.length % 2).reversed()

    print(if (left == right) "yes" else "no")
}