import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val range1Min = scanner.nextInt()
    val range1Max = scanner.nextInt()
    val range2Min = scanner.nextInt()
    val range2Max = scanner.nextInt()
    val num = scanner.nextInt()

    println(num in range1Min..range1Max || num in range2Min..range2Max)
}