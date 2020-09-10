import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val gradeNum = scanner.nextInt()
    var a = 0
    var b = 0
    var c = 0
    var d = 0

    repeat(gradeNum) {
        val grade = scanner.nextInt()
        if (grade == 2) {
            d++
        } else if (grade == 3) {
            c++
        } else if (grade == 4) {
            b++
        } else {
            a++
        }
    }

    println("$d $c $b $a")
}