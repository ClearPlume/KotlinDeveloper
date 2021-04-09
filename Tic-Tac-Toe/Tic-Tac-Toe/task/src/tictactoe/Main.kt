package tictactoe

import kotlin.math.abs

const val DRAW = '\u0000'
const val IMPOSSIBLE = '\u0001'
const val NOT_FINISHED = '\u0002'
const val gridSize = 3

fun main() {
    // write your code here
    print("Enter cells: ")
    val cells = readLine()!!.chunked(gridSize).map { it.replace('_', ' ') }

    println("---------")
    cells.forEach {
        print("| ")
        for (i in it.indices) {
            print(
                if (i < it.lastIndex) {
                    "${it.substring(i, i + 1)} "
                } else {
                    it.substring(i, i + 1)
                }
            )
        }
        println(" |")
    }
    println("---------")

    val winner = cells.whoWin()
    println(
        when (winner) {
            DRAW -> "Draw"
            IMPOSSIBLE -> "Impossible"
            NOT_FINISHED -> "Game not finished"
            else -> "$winner wins"
        }
    )
}

fun List<String>.whoWin(): Char {
    val xNum = this count 'X'
    val oNum = this count 'O'
    val spaceNum = this count ' '
    val sameChars = mutableListOf<Char>()

    if (abs(xNum - oNum) > 1) {
        return IMPOSSIBLE
    }

    // Detect three horizontal lines
    for (row in this) {
        var same = true
        var lastCell = row[0]

        for (cell in row) {
            same = same && lastCell == cell
            lastCell = cell
        }

        if (same) {
            sameChars.add(lastCell)
        }
    }

    // Detect three vertical lines
    for (curCol in 0 until gridSize) {
        var same = true
        var lastCell = this[0][curCol]

        for (row in 0 until gridSize) {
            same = same && lastCell == this[row][curCol]
            lastCell = this[row][curCol]
        }

        if (same) {
            sameChars.add(lastCell)
        }
    }

    // Detect two slashes
    run {
        var same = true
        var lastCell = this[0][0]

        for (i in 0 until gridSize) {
            same = same && lastCell == this[i][i]
            lastCell = this[i][i]
        }

        if (same) {
            sameChars.add(lastCell)
        }
    }

    run {
        var same = true
        var lastCell = this[0][2]

        var curRow = 0
        var curCol = gridSize - 1
        do {
            same = same && lastCell == this[curRow][curCol]
            lastCell = this[curRow][curCol]

            curRow++
            curCol--
        } while (curRow < gridSize)

        if (same) {
            sameChars.add(lastCell)
        }
    }

    return when (sameChars.size) {
        0 -> if (spaceNum > 0) NOT_FINISHED else DRAW
        1 -> sameChars[0]
        else -> IMPOSSIBLE
    }
}

infix fun List<String>.count(tar: Char): Int {
    var num = 0

    for (row in this) {
        num += row.count { it == tar }
    }

    return num
}
