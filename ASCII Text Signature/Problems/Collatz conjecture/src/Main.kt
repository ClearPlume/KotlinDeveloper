import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    var num = scanner.nextInt()

    while (num != 1) {
        print("$num ")
        if (num % 2 == 0) {
            num /= 2
        } else {
            num = num * 3 + 1
        }
    }

    print(1)
}