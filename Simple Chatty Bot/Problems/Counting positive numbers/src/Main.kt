import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val numOfSequence = scanner.nextInt()

    var numOfPositive = 0

    repeat(numOfSequence) {
        if (scanner.nextInt() > 0) {
            numOfPositive++
        }
    }

    println(numOfPositive)
}