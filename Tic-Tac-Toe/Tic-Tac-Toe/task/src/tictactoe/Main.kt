package tictactoe

import kotlin.math.abs

const val DRAW = '\u0000'
const val IMPOSSIBLE = '\u0001'
const val NOT_FINISHED = '\u0002'
const val gridSize = 3

fun main() {
    // write your code here
    print("Enter cells: ")
    val cells = readLine()!!.chunked(gridSize).map { it.replace('_', ' ') }.toMutableList()

    show(cells)

    var x = -1
    var y = -1

    while (x == -1 && y == -1) {
        print("Enter the coordinates: ")
        val coordinates = readLine()!!.split(' ')

        if (coordinates.size < 2) {
            println("You should enter numbers!")
            continue
        }

        val numRegex = Regex("^\\d+$")
        if (coordinates[0].matches(numRegex) && coordinates[1].matches(numRegex)) {
            x = coordinates[0].toInt()
            y = coordinates[1].toInt()
        } else {
            println("You should enter numbers!")
            continue
        }

        if (x > gridSize || y > gridSize) {
            println("Coordinates should be from 1 to 3!")
            x = -1
            y = -1
            continue
        }

        if (cells[x - 1][y - 1] != ' ') {
            println("This cell is occupied! Choose another one!")
            x = -1
            y = -1
        }
    }

    x--
    y--
    cells[x, y] = 'X'

    show(cells)

    // val winner = whoWin(cells)
    // println(
    //     when (winner) {
    //         DRAW -> "Draw"
    //         IMPOSSIBLE -> "Impossible"
    //         NOT_FINISHED -> "Game not finished"
    //         else -> "$winner wins"
    //     }
    // )
}

fun show(cells: List<String>) {
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
}

fun whoWin(cells: List<String>): Char {
    val xNum = cells count 'X'
    val oNum = cells count 'O'
    val spaceNum = cells count ' '
    val sameChars = mutableListOf<Char>()

    if (abs(xNum - oNum) > 1) {
        return IMPOSSIBLE
    }

    // Detect three horizontal lines
    for (row in cells) {
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
        var lastCell = cells[0][curCol]

        for (row in 0 until gridSize) {
            same = same && lastCell == cells[row][curCol]
            lastCell = cells[row][curCol]
        }

        if (same) {
            sameChars.add(lastCell)
        }
    }

    // Detect two slashes
    run {
        var same = true
        var lastCell = cells[0][0]

        for (i in 0 until gridSize) {
            same = same && lastCell == cells[i][i]
            lastCell = cells[i][i]
        }

        if (same) {
            sameChars.add(lastCell)
        }
    }

    run {
        var same = true
        var lastCell = cells[0][2]

        var curRow = 0
        var curCol = gridSize - 1
        do {
            same = same && lastCell == cells[curRow][curCol]
            lastCell = cells[curRow][curCol]

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

private operator fun MutableList<String>.set(row: Int, col: Int, value: Char) {
    val rowChars = this[row].toCharArray()
    rowChars[col] = value
    this[row] = String(rowChars)
}

private infix fun List<String>.count(tar: Char): Int {
    var num = 0

    for (row in this) {
        num += row.count { it == tar }
    }

    return num
}
