import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    // duration in days
    val duration = scanner.nextInt()
    // total food cost per day
    val foodCost = scanner.nextInt()
    // one-way flight cost
    val flight = scanner.nextInt()
    // cost of one night in a hotel
    val hotel = scanner.nextInt()

    println(duration * foodCost + hotel * (duration - 1) + flight * 2)
}