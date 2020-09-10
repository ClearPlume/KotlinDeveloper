import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val inputNum = scanner.nextInt()
    var maxInput = 0

    repeat(inputNum) {
        val input = scanner.nextInt()
        if (input % 4 == 0) {
            if (input > maxInput) {
                maxInput = input
            }
        }
    }

    println(maxInput)
}