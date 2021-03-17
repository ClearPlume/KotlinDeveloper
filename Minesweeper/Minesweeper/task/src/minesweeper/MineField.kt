package minesweeper

import minesweeper.Cell.State
import kotlin.random.Random

class MineField(private val fieldSize: Int, private val mineNum: Int) {
    // Data used as cell display
    private val fieldView: Array<CharArray> = Array(fieldSize) { CharArray(fieldSize) { State.UN_EXPLORED.symbol } }

    // Cells in the game
    private val cells: Array<Array<Cell>> = Array(fieldSize) { Array(fieldSize) { Cell() } }

    init {
        // Number of Mines currently generated
        var curMineNum = 0
        // Array of cells with mines, used as identification, to avoid
        // randomly generated multiple mines in the same location
        val mines: Array<Cell> = Array(mineNum) { Cell(state = State.MINE) }

        while (curMineNum < mineNum) {
            val y = Random.nextInt(fieldSize)
            val x = Random.nextInt(fieldSize)

            val cell = Cell(x, y)

            if (!mines.contains(cell)) {
                mines[curMineNum].x = x
                mines[curMineNum].y = y

                cells[x][y].x = x
                cells[x][y].y = y
                cells[x][y].state = State.MINE

                curMineNum++
            }
        }
    }

    fun calcMineNum() {
        for (x in fieldView[0].indices) {
            for (y in fieldView.indices) {
                var aroundMines = 0

                /*if (fieldView[x][y] == ' ') {
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
                }*/
            }
        }
    }

    /**
     * Show "game interface"
     */
    fun showMineField() {
        println(" |123456789|")
        println("-|---------|")
        for (field in fieldView) {
            print(fieldView.indexOf(field) + 1)
            print("|")
            print(field)
            println("|")
        }
        println("-|---------|")
    }

    /**
     *  Player annotates the status of the specified cell
     */
    fun action(cell: Cell, input: String) {
        val cellSymbol = fieldView[cell.y][cell.x]

        if (cellSymbol.isDigit()) {
            println("There is a number here!")
            return
        }

        if ("mine" == input) {
            // Player thinks the cell has a mine
            if (fieldView[cell.y][cell.x] == State.MARKED_MINE.symbol) {
                fieldView[cell.y][cell.x] = State.UN_EXPLORED.symbol
            } else {
                fieldView[cell.y][cell.x] = State.MARKED_MINE.symbol
            }
        } else {
            // Player thinks the cell is empty
            fieldView[cell.y][cell.x] = State.EXPLORED_FREE.symbol
        }
    }

    /**
     *  Judge the state of the game
     *
     * @return Is the game finished
     */
    fun checkGameState(): Boolean {
        return true
    }

    fun win() = println("Congratulations! You found all the mines!")

    fun failed() = println("You stepped on a mine and failed!")
}