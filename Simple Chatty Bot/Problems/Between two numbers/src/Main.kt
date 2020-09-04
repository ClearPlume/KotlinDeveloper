import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val num = scanner.nextInt()
    var rangeMin = scanner.nextInt()
    var rangeMax = scanner.nextInt()

    if (rangeMin > rangeMax) {
        val tmp = rangeMin
        rangeMin = rangeMax
        rangeMax = tmp
    }

    println(num in rangeMin..rangeMax)
}