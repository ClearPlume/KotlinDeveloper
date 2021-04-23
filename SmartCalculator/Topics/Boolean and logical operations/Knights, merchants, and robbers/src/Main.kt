fun main() {
    // write your code here
    val (first, second, confession) = Array(3) { readLine().toBoolean() }
    println(!first.xor(second) && !confession)
}