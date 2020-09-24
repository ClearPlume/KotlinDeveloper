import java.util.*

fun main(args: Array<String>) {
    val input = Scanner(System.`in`)

    val char = input.next()[0]

    for (c in 'a'..'z') {
        if (c == char) {
            break
        }

        print(c)
    }
}