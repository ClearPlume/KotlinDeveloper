import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val num = scanner.nextInt()

    println(when (num) {
        in 0..9 -> 1
        in 10..99 -> 2
        in 100..999 -> 3
        else -> 4
    })
}