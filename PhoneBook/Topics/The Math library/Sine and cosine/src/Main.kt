import kotlin.math.cos
import kotlin.math.sin

fun main() {
    // write your code here
    val radians = readLine()!!.toDouble()
    println(sin(radians) - cos(radians))
}