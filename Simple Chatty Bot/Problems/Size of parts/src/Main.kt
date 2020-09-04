import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    val partsNum = scanner.nextInt()
    var readyNum = 0
    var fixNum = 0
    var rejectNum = 0

    repeat(partsNum) {
        val part = scanner.nextInt()
        if (part > 0) {
            fixNum++
        } else if (part < 0) {
            rejectNum++
        } else {
            readyNum++
        }
    }

    println("$readyNum $fixNum $rejectNum")
}