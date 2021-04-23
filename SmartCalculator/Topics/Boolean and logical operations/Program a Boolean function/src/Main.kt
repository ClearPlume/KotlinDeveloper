fun main() {
    // write your code here
    val (x, y, z) = Array(3) { readLine().toBoolean() }
    println(!(x && y) || z)
}