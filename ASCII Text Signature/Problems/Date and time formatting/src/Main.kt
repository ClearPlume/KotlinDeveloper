import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val hour = scanner.nextInt()
    val minute = scanner.nextInt()
    val second = scanner.nextInt()
    val day = scanner.nextInt()
    val month = scanner.nextInt()
    val year = scanner.nextInt()

    println("$hour:$minute:$second $day/$month/$year")
}