import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val n = scanner.nextInt()
    var sum = 0

    for (i in 1..n) {
        sum += scanner.nextInt()
    }

    println(sum)
}