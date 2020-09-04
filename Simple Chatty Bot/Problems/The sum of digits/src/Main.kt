import java.util.Scanner

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    var num = scanner.nextInt()
    var sum = 0

    while (num > 0) {
        sum += num % 10
        num /= 10
    }

    println(sum)
}