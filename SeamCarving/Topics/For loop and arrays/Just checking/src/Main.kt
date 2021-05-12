import java.util.*

fun main() {
    // write your code here
    val scanner = Scanner(System.`in`)
    val numbers = Array(scanner.nextInt()) { scanner.nextInt() }
    val (p, m) = Array(2) { scanner.nextInt() }

    println(if (p in numbers && m in numbers) "YES" else "NO")
}