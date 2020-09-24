import java.util.*

fun main(args: Array<String>) {
    val input = Scanner(System.`in`)

    val word = input.next()

    for (c in 'a'..'z') {
        if (word.contains(c)) {
            continue
        }
        print(c)
    }
}