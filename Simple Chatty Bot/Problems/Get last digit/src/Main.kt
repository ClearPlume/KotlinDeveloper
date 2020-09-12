import java.util.*
import kotlin.math.abs

// write your code here
fun getLastDigit(num: Int) = abs(num % 10)

/* Do not change code below */
fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    val a = scanner.nextInt()
    println(getLastDigit(a))
}