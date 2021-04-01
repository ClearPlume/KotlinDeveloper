import java.math.BigInteger

fun main() {
    // write your code here
    val (a, b, c, d) = Array(4) { BigInteger(readLine()!!) }
    println(-a * b + c - d)
}