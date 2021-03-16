package minesweeper

import kotlin.random.Random

class MineField(private val fieldSize: Int, private val mineNum: Int) {
    private var running = true
    private var steppedMine = false

    private val fieldView: Array<CharArray> = Array(fieldSize) { CharArray(fieldSize) { '.' } }
    private val fieldData: Array<CharArray> = Array(fieldSize) { CharArray(fieldSize) { '\u0000' } }
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
        for (x in fieldView[0].indices) {
            for (y in fieldView.indices) {
                var aroundMines = 0

                if (fieldView[x][y] == '.') {
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
                    if (x + 1 <= fieldView[0].lastIndex && y - 1 >= 0) {
                        if (mines.contains(Cell(x + 1, y - 1))) {
                            aroundMines++
                        }
                    }
                    if (x - 1 >= 0) {
                        if (mines.contains(Cell(x - 1, y))) {
                            aroundMines++
                        }
                    }
                    if (x + 1 <= fieldView[0].lastIndex) {
                        if (mines.contains(Cell(x + 1, y))) {
                            aroundMines++
                        }
                    }
                    if (x - 1 >= 0 && y + 1 <= fieldView.lastIndex) {
                        if (mines.contains(Cell(x - 1, y + 1))) {
                            aroundMines++
                        }
                    }
                    if (y + 1 <= fieldView.lastIndex) {
                        if (mines.contains(Cell(x, y + 1))) {
                            aroundMines++
                        }
                    }
                    if (x + 1 <= fieldView[0].lastIndex && y + 1 <= fieldView.lastIndex) {
                        if (mines.contains(Cell(x + 1, y + 1))) {
                            aroundMines++
                        }
                    }
                    if (aroundMines > 0 && !mines.contains(Cell(x, y))) {
                        fieldView[x][y] = '0' + aroundMines
                    }
                }
            }
        }
    }

    fun showMineField() {
        println(" |123456789|")
        println("-|---------|")
        for (f in fieldView) {
            print(fieldView.indexOf(f) + 1)
            print("|")
            print(f)
            println("|")
        }
        println("-|---------|")
    }

    fun getCellSymbol(x: Int, y: Int): Char {
        return fieldView[y][x]
    }

    fun setCellSymbol(x: Int, y: Int, c: Char) {
        fieldView[y][x] = c
    }

    fun getMarkedCells(): Array<Cell> {
        val cells = ArrayList<Cell>()

        for (x in fieldView[0].indices) {
            for (y in fieldView.indices) {
                if (fieldView[x][y] == '*') {
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

        return win && steppedMine
    }

    fun win() = println("Congratulations! You found all the mines!")

    fun failed() = println("You stepped on a mine and failed!")

    fun getRunning(): Boolean = running

    fun setRunning(running: Boolean) {
        this.running = running
    }


}