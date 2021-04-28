fun main() {
    // put your code here
    val num = readLine()!!.toDouble()
    // (10.53 - 10) * 10 -> 0.53 * 10 -> 5.3
    println(((num - num.toInt()) * 10).toString()[0])
}