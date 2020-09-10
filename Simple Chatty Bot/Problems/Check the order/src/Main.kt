import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val numberOfNum = scanner.nextInt()
    var orderedByAscend = true
    var firstNum = scanner.nextInt()

    repeat(numberOfNum - 1) {
        val nextInt = scanner.nextInt()
        orderedByAscend = firstNum <= nextInt && orderedByAscend
        firstNum = nextInt
    }

    println(if (orderedByAscend) "YES" else "NO")
}