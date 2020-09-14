import java.util.Scanner

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val celsius = scanner.nextDouble()

    println(celsius * 1.8 + 32)
}