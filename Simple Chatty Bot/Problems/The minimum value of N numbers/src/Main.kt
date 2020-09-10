import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val numberOfNum = scanner.nextInt()
    var minimum = 9999

    repeat(numberOfNum) {
        val newInt = scanner.nextInt()
        if (minimum > newInt) {
            minimum = newInt
        }
    }

    println(minimum)
}