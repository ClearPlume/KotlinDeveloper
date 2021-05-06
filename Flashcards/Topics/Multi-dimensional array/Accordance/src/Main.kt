fun main() {
    // put your code here
    val matrix = Array(2) { r -> Array(3) { c -> "[$r][$c]" } }
    println(matrix.contentDeepToString())
}