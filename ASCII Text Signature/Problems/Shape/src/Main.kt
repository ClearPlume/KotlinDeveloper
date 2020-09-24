import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val choice = scanner.nextInt()

    if (choice in 1..4) {
        println("You have chosen a ${when (choice) {
            1 -> "square"
            2 -> "circle"
            3 -> "triangle"
            else -> "rhombus"
        }}")
    } else {
        println("There is no such shape!")
    }

}