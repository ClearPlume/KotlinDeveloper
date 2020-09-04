import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val a = scanner.nextInt()
    val b = scanner.nextInt()
    val c = scanner.nextInt()

    println(if (a > c) {
        if (b > a) {
            b
        } else {
            a
        }
    } else {
        if (b > c) {
            b
        } else {
            c
        }
    })
}