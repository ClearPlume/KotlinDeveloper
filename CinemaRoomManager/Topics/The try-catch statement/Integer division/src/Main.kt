fun intDivision(x: String, y: String): Int {
    val a: Int
    val b: Int
    var result = 0

    try {
        a = x.toInt()
        b = y.toInt()

        result = a / b
    } catch (e: NumberFormatException) {
        println("Read values are not integers.")
    } catch (e: ArithmeticException) {
        println("Exception: division by zero!")
    }

    return result
}

fun main() {
    val x = readLine()!!
    val y = readLine()!!
    println(intDivision(x, y))
}