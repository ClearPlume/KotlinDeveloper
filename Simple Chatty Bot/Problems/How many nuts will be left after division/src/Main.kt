import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val squirrels = scanner.nextInt()
    val nuts = scanner.nextInt()

    println(nuts % squirrels)
}