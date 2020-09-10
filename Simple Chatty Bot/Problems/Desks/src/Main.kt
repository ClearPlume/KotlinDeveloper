import java.util.Scanner

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    var num = 0

    while (scanner.hasNext()) {
        val stuNum = scanner.nextInt()
        num += stuNum / 2 + stuNum % 2
    }

    println(num)
}