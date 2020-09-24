import java.util.*

fun main() {
    val input = Scanner(System.`in`)

    val a = input.nextInt()
    var ai = 0
    val b = input.nextInt()
    var bi = 0
    val c = input.nextInt()
    var ci = 0
    val n = input.nextInt()
    var ni = 0
    var password = ""

    while (ai < a || bi < b || ci < c) {
        if (ai < a) {
            password += (ai % 26 + 65).toChar()
            ai++
            ni++
        }

        if (bi < b) {
            password += (bi % 26 + 97).toChar()
            bi++
            ni++
        }

        if (ci < c) {
            password += ci % 10
            ci++
            ni++
        }
    }

    while (ni < n) {
        password += (ni + 65).toChar()
        ni++
    }

    println(password)
}