import java.util.Scanner

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    var inValue: Int = scanner.nextInt()
    var outValue: Int = inValue % 10 * 100
    inValue /= 10
    outValue += inValue % 10 * 10
    inValue /= 10
    outValue += inValue

    println(outValue)
}