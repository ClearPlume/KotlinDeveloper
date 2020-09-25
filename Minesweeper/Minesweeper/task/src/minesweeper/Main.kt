package minesweeper

fun main() {
    val field = arrayOf(
            charArrayOf('.', '.', '.', '.', '.', '.', '.', 'X', '.'),
            charArrayOf('.', 'X', '.', '.', '.', '.', '.', '.', '.'),
            charArrayOf('.', '.', '.', '.', 'X', '.', '.', '.', '.'),
            charArrayOf('.', '.', '.', '.', '.', '.', '.', '.', 'X'),
            charArrayOf('.', '.', '.', 'X', '.', '.', 'X', '.', '.'),
            charArrayOf('.', '.', '.', '.', '.', '.', '.', '.', '.'),
            charArrayOf('.', '.', '.', 'X', '.', '.', '.', '.', '.'),
            charArrayOf('.', '.', 'X', '.', '.', 'X', '.', '.', '.'),
            charArrayOf('.', '.', '.', '.', '.', '.', '.', 'X', '.')
    )
    for (f in field) {
        println(f)
    }
}