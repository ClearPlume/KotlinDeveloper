import java.util.*
import kotlin.math.floor
import kotlin.math.roundToInt

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    val population = scanner.nextDouble()
    println(floor(Math.cbrt(population)).roundToInt())
}