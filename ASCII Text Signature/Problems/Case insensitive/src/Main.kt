import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)

    val str1 = scanner.nextLine()
    val str2 = scanner.nextLine()

    println(str1.equals(str2, true))
}