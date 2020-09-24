import java.util.*

fun main() {
    val input = Scanner(System.`in`)

    val string = input.next().toLowerCase()
    var numOfCG = 0

    for (c in string) {
        if (c == 'g' || c == 'c') {
            numOfCG++
        }
    }

    print(numOfCG.toDouble() / string.length * 100)
}