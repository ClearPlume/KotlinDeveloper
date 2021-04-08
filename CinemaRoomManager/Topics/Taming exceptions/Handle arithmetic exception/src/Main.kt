import java.util.*

fun main() {
    // put your code here
    val scanner = Scanner(System.`in`)
    val (a, b) = IntArray(2) { scanner.nextInt() }

    if (b == 0) {
        println("Division by zero, please fix the second argument!")
        return
    }

    println(a / b)
}