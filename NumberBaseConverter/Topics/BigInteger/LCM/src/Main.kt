import java.math.BigInteger
import java.util.*

fun main() {
    // write your code here
    val scanner = Scanner(System.`in`)
    val (a, b) = Array(2) { BigInteger(scanner.next()) }
    println(a * b / a.gcd(b))
}