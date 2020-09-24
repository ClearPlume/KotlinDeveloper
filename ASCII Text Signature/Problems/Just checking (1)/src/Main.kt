import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val arrSize = scanner.nextInt()
    val arr = IntArray(arrSize)

    for (i in arr.indices) {
        arr[i] = scanner.nextInt()
    }

    val m = scanner.nextInt()
    val n = scanner.nextInt()
    var occurNext = false

    arr.forEachIndexed { index, value ->
        if (value == m || value == n) {
            occurNext = when (index) {
                0 -> {
                    occurNext || arr[1] == n
                }
                arr.lastIndex -> {
                    occurNext || arr[index - 1] == n
                }
                else -> {
                    occurNext || arr[index + 1] == n || arr[index - 1] == n
                }
            }
        }
    }

    println(if (occurNext) "YES" else "NO")
}