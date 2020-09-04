import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val reeses = scanner.nextInt()
    val week = scanner.nextBoolean()

    if (week) {
        println(reeses >= 15 && reeses <= 25)
    } else {
        println(reeses >= 10 && reeses <= 20)
    }
}