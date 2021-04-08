import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)
    println(IntArray(3) { scanner.nextInt() }.sum())
}