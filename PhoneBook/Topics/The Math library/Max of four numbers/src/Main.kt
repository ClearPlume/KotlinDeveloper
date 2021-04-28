import kotlin.math.max

fun main() {
    // write your code here
    val (a, b, c, d) = Array(4) { readLine()!!.toInt() }
    println(max(max(a, b), max(c, d)))
}