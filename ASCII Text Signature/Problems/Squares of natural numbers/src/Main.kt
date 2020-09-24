import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val n = scanner.nextInt()
    var squar = 1

    while (squar * squar <= n) {
        println(squar * squar)
        squar++
    }
}