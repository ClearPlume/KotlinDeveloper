package cinema

fun main() {
    // write your code here
    val colNum = 9
    val seats = Array(8) { col ->
        if (col == 0) {
            Array(colNum) { row ->
                if (row == 0) {
                    "  "
                } else {
                    "$row${if (row < colNum - 1) ' ' else '\u0000'}"
                }
            }
        } else {
            Array(colNum) { row ->
                if (row == 0) {
                    "$col "
                } else {
                    "S${if (row < colNum - 1) ' ' else '\u0000'}"
                }
            }
        }
    }

    println("Cinema:")
    seats.forEach {
        it.forEach { seat -> print(seat) }
        println()
    }
}