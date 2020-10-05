package minesweeper

import kotlin.random.Random

class MineField(private val fieldSize: Int, private val mineNum: Int) {
    private val field: Array<CharArray> = Array(fieldSize) { CharArray(fieldSize) { '.' } }
    private val mines: Array<Cell> = Array(mineNum) { Cell() }

    fun initial() {
        var curMineNum = 0

        while (curMineNum < mineNum) {
            val y = Random.nextInt(fieldSize)
            val x = Random.nextInt(fieldSize)

            val cell = Cell(x, y)

            if (!mines.contains(cell)) {
                mines[curMineNum].x = x
                mines[curMineNum].y = y
                curMineNum++
            }
        }
    }

    fun calcMineNum() {
        for (x in field[0].indices) {
            for (y in field.indices) {
                var aroundMines = 0

                if (field[x][y] == '.') {
                    if (x - 1 >= 0 && y - 1 >= 0) {
                        if (mines.contains(Cell(x - 1, y - 1))) {
                            aroundMines++
                        }
                    }
                    if (y - 1 >= 0) {
                        if (mines.contains(Cell(x, y - 1))) {
                            aroundMines++
                        }
                    }
                    if (x + 1 <= field[0].lastIndex && y - 1 >= 0) {
                        if (mines.contains(Cell(x + 1, y - 1))) {
                            aroundMines++
                        }
                    }
                    if (x - 1 >= 0) {
                        if (mines.contains(Cell(x - 1, y))) {
                            aroundMines++
                        }
                    }
                    if (x + 1 <= field[0].lastIndex) {
                        if (mines.contains(Cell(x + 1, y))) {
                            aroundMines++
                        }
                    }
                    if (x - 1 >= 0 && y + 1 <= field.lastIndex) {
                        if (mines.contains(Cell(x - 1, y + 1))) {
                            aroundMines++
                        }
                    }
                    if (y + 1 <= field.lastIndex) {
                        if (mines.contains(Cell(x, y + 1))) {
                            aroundMines++
                        }
                    }
                    if (x + 1 <= field[0].lastIndex && y + 1 <= field.lastIndex) {
                        if (mines.contains(Cell(x + 1, y + 1))) {
                            aroundMines++
                        }
                    }
                    if (aroundMines > 0 && !mines.contains(Cell(x, y))) {
                        field[x][y] = '0' + aroundMines
                    }
                }
            }
        }
    }

    fun showMineField() {
        println(" |123456789|")
        println("-|---------|")
        for (f in field) {
            print(field.indexOf(f) + 1)
            print("|")
            print(f)
            println("|")
        }
        println("-|---------|")
    }

    fun getCellSymbol(x: Int, y: Int): Char {
        return field[y][x]
    }

    fun setCellSymbol(x: Int, y: Int, c: Char) {
        field[y][x] = c
    }

    fun getMarkedCells(): Array<Cell> {
        val cells = ArrayList<Cell>()

        for (x in field[0].indices) {
            for (y in field.indices) {
                if (field[x][y] == '*') {
                    cells.add(Cell(x, y))
                }
            }
        }

        return cells.toTypedArray()
    }

    fun isWin(): Boolean {
        var win = false

        val cells = getMarkedCells()

        win = if (cells.size == mineNum) {
            for (c in cells) {
                if (mines.contains(c)) {
                    win = true
                } else {
                    win = false
                    break
                }
            }
            win
        } else {
            false
        }

        return win
    }

    fun win() {
        println("Congratulations! You found all the mines!")
    }
}