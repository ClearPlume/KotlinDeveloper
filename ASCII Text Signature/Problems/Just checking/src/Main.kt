import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    println(when (scanner.nextInt()) {
        1, 3, 4 -> "No!"
        2 -> "Yes!"
        else -> "Unknown number"
    })
}