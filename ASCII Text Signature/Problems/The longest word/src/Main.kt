import java.util.*

fun main() {
    val input = Scanner(System.`in`)

    val words = input.nextLine().split(' ')
    var longestIndex = 0

    for (i in 1..words.lastIndex) {
        if (words[i].length > words[longestIndex].length) {
            longestIndex = i
        }
    }

    print(words[longestIndex])
}