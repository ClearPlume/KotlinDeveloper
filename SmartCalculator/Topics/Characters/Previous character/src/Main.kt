import java.util.*

fun main() {
    // write your code here
    val scanner = Scanner(System.`in`)

    val chars = Array(4) { scanner.next()[0] }
    for (char in chars) {
        println(char - 1)
    }
}