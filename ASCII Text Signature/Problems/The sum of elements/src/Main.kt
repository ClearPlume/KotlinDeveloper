import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    var sum = 0
    var tmp = scanner.nextInt()

    while (tmp != 0) {
        sum += tmp
        tmp = scanner.nextInt()
    }

    println(sum)
}