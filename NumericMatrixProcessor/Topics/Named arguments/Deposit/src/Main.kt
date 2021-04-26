import kotlin.math.pow

fun main() {
    // write your code here
    when (readLine()!!) {
        "amount" -> println(deposit(amount = readLine()!!.toInt()))
        "percent" -> println(deposit(percent = readLine()!!.toInt()))
        "years" -> println(deposit(years = readLine()!!.toInt()))
    }
}

fun deposit(amount: Int = 1000, percent: Int = 5, years: Int = 10): Int {
    return (amount * (1 + percent.toDouble() / 100).pow(years)).toInt()
}