import java.util.*

fun main() {
    // write your code here
    val scanner = Scanner(System.`in`)
    val num = scanner.nextInt()
    val char = scanner.next()[0]
    println(char.toInt() == num)
}