import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val num = scanner.nextInt()

    if (num % 2 == 0) {
        println("Divided by 2")
    }

    if (num % 3 == 0) {
        println("Divided by 3")
    }

    if (num % 5 == 0) {
        println("Divided by 5")
    }

    if (num % 6 == 0) {
        println("Divided by 6")
    }
}