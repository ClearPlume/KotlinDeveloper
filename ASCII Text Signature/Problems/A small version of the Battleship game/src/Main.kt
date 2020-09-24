import java.util.*

fun main(args: Array<String>) {
    val input = Scanner(System.`in`)

    val xs = IntArray(3)
    val ys = IntArray(3)

    for (i in 0..2) {
        xs[i] = input.nextInt()
        ys[i] = input.nextInt()
    }

    println(getVacant(xs))
    println(getVacant(ys))
}

fun getVacant(arr: IntArray): String {
    var vacant = ""

    for (i in 1..5) {
        if (!arr.contains(i)) {
            vacant += i
        }
        if (i <= 4 && !arr.contains(i)) {
            vacant += ' '
        }
        continue
    }

    return vacant.trim()
}