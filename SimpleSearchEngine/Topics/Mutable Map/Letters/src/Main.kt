import java.util.*

fun main() {
    val letters = mutableMapOf<Int, Char>()
    val scanner = Scanner(System.`in`)
    var letterNum = 1

    do {
        val char = scanner.next()[0]
        letters[letterNum++] = char
    } while (char != 'z')

    print(letters[4])
}