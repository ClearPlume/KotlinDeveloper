fun main() {
    // put your code here
    val word = readLine()!!
    println("${word.last()}${word.substring(1 until word.lastIndex)}${word.first()}")
}