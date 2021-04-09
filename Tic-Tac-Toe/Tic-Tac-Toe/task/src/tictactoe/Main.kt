package tictactoe

const val GRID_SIZE = 3

enum class State {
    X_WIN, O_WIN, DRAW, NOT_FINISHED
}

fun main() {
    // write your code here
    val cells = MutableList(GRID_SIZE) { "   " }
    var xStep = true
    var state: State

    show(cells)

    do {
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

            if (x > GRID_SIZE || y > GRID_SIZE) {
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
        cells[x, y] = if (xStep) 'X' else 'O'
        xStep = !xStep
        show(cells)
        state = checkGameState(cells)
    } while (state == State.NOT_FINISHED)

    println(
        when (state) {
            State.O_WIN -> "O wins"
            State.X_WIN -> "X wins"
            else -> "Draw"
        }
    )
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

fun checkGameState(cells: List<String>): State {
    val spaceNum = cells count ' '
    val sameChars = mutableListOf<Char>()

    // Detect three horizontal lines
    for (row in cells) {
        var same = true
        var lastCell = row[0]

        for (cell in row) {
            same = same && lastCell == cell
            lastCell = cell
        }

        if (same && lastCell != ' ') {
            sameChars.add(lastCell)
        }
    }

    // Detect three vertical lines
    for (curCol in 0 until GRID_SIZE) {
        var same = true
        var lastCell = cells[0][curCol]

        for (row in 0 until GRID_SIZE) {
            same = same && lastCell == cells[row][curCol]
            lastCell = cells[row][curCol]
        }

        if (same && lastCell != ' ') {
            sameChars.add(lastCell)
        }
    }

    // Detect two slashes
    run {
        var same = true
        var lastCell = cells[0][0]

        for (i in 0 until GRID_SIZE) {
            same = same && lastCell == cells[i][i]
            lastCell = cells[i][i]
        }

        if (same && lastCell != ' ') {
            sameChars.add(lastCell)
        }
    }

    run {
        var same = true
        var lastCell = cells[0][2]

        var curRow = 0
        var curCol = GRID_SIZE - 1
        do {
            same = same && lastCell == cells[curRow][curCol]
            lastCell = cells[curRow][curCol]

            curRow++
            curCol--
        } while (curRow < GRID_SIZE)

        if (same && lastCell != ' ') {
            sameChars.add(lastCell)
        }
    }

    return if (sameChars.size == 0) {
        if (spaceNum > 0) {
            State.NOT_FINISHED
        } else {
            State.DRAW
        }
    } else {
        if (sameChars[0] == 'O') {
            State.O_WIN
        } else {
            State.X_WIN
        }
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
