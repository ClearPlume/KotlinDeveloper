import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val age = scanner.nextInt()

    println(age in 18..59)
}