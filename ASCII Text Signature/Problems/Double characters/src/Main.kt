import java.util.*

fun main() {
    val string = Scanner(System.`in`).next()

    for (s in string) {
        repeat(2) {
            print(s)
        }
    }
}