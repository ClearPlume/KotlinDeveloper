import java.util.*

fun main() {
    val input = Scanner(System.`in`)

    var first = input.nextLine()
    val second = input.nextLine()
    var num = 0

    while (first.indexOf(second) != -1) {
        num++
        first = first.replaceFirst(second, "")
    }

    println(num)
}