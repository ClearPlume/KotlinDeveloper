import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val n = scanner.nextInt()
    var num = 1

    for (i in 1..n) {
        for (j in 1..i) {
            print(i)
            num++
            if (num > n) {
                return
            }
            print(' ')
        }
    }
}