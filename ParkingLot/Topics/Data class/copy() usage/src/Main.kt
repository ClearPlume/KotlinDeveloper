import java.util.*

// do not change this data class
data class Box(val height: Int, val length: Int, val width: Int)

fun main() {
    // implement your code here
    val scanner = Scanner(System.`in`)
    val (height, lengthA, lengthB, width) = Array(4) { scanner.nextInt() }

    val boxA = Box(height, lengthA, width)
    val boxB = boxA.copy(length = lengthB)

    println(boxA)
    println(boxB)
}