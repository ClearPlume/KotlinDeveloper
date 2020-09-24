import java.util.*

fun main() {
    val string = Scanner(System.`in`).next()
    var ordered = true

    for (i in string.indices) {
        if (i == string.lastIndex) {
            break
        }

        ordered = string[i] + 1 == string[i + 1] && ordered
    }

    print(ordered)
}