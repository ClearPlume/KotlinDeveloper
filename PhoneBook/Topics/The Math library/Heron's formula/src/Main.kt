import kotlin.math.sqrt

fun main() {
    // put your code here
    val (a, b, c) = Array(3) { readLine()!!.toInt() }
    val p = (a + b + c) / 2.0
    println(sqrt(p * (p - a) * (p - b) * (p - c)))
}