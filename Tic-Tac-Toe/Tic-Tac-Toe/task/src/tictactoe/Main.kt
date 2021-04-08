package tictactoe

fun main() {
    // write your code here
    val gridSize = 3

    print("Enter cells: ")
    val cells = readLine()?.chunked(gridSize)?.map {
        var row = ""

        for (i in it.indices.reversed()) {
            row = if (i > 0) {
                " ${it.substring(i, i + 1)}" + row
            } else {
                it.substring(i, i + 1) + row
            }
        }

        row
    }

    println("---------")
    cells?.forEach { println("| $it |") }
    println("---------")
}