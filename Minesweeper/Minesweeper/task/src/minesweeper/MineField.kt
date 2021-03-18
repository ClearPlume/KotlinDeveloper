package minesweeper

import minesweeper.Cell.State
import kotlin.random.Random

class MineField(private val fieldSize: Int, mineNum: Int) {
    // Data used as cell display
    private val fieldView: Array<CharArray> = Array(fieldSize) { CharArray(fieldSize) { State.UN_EXPLORED.symbol } }

    // Cells in the game
    private val cells: Array<Array<Cell>> = Array(fieldSize) { Array(fieldSize) { Cell() } }

    // Did the player step on a mine
    private var steppedMine: Boolean = false

    init {
        // Number of Mines currently generated
        var curMineNum = 0

        while (curMineNum < mineNum) {
            val y = Random.nextInt(fieldSize)
            val x = Random.nextInt(fieldSize)

            if (cells[x][y].state != State.MINE) {
                cells[x][y].state = State.MINE
                curMineNum++
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

            // The player stepped on the mine
            if (State.MINE == cells[cell.y][cell.x].state) {
                steppedMine = true
                return
            }

            exploreCell(cell)
        }
    }

    private fun exploreCell(cell: Cell) {
        if (aroundCellHaveMine(cell)) {
            return
        }
    }

    /**
     * Determine if there are mines around the cell TODO 测试
     */
    private fun aroundCellHaveMine(cell: Cell): Boolean {
        var aroundMines = 0

        if (cell.x - 1 >= 0 && cell.y - 1 >= 0) {
            if (cells[cell.y - 1][cell.x - 1].state == State.MINE) {
                aroundMines++
            }
        }
        if (cell.y - 1 >= 0) {
            if (cells[cell.x][cell.y - 1].state == State.MINE) {
                aroundMines++
            }
        }
        if (cell.x + 1 <= fieldView[0].lastIndex && cell.y - 1 >= 0) {
            if (cells[cell.x + 1][cell.y - 1].state == State.MINE) {
                aroundMines++
            }
        }
        if (cell.x - 1 >= 0) {
            if (cells[cell.x - 1][cell.y].state == State.MINE) {
                aroundMines++
            }
        }
        if (cell.x + 1 <= fieldView[0].lastIndex) {
            if (cells[cell.x + 1][cell.y].state == State.MINE) {
                aroundMines++
            }
        }
        if (cell.x - 1 >= 0 && cell.y + 1 <= fieldView.lastIndex) {
            if (cells[cell.x - 1][cell.y + 1].state == State.MINE) {
                aroundMines++
            }
        }
        if (cell.y + 1 <= fieldView.lastIndex) {
            if (cells[cell.x][cell.y + 1].state == State.MINE) {
                aroundMines++
            }
        }
        if (cell.x + 1 <= fieldView[0].lastIndex && cell.y + 1 <= fieldView.lastIndex) {
            if (cells[cell.x + 1][cell.y + 1].state == State.MINE) {
                aroundMines++
            }
        }
        return aroundMines != 0
    }

    /**
     * Check the state of the game
     *
     * @return Is the game finished
     */
    fun checkGameState(): Boolean {
        // The player stepped on the mine
        if (steppedMine) {

            // ...print the field in its current state
            for (i in cells.indices) {
                val cellRow = cells[i]

                for (j in cellRow.indices) {
                    if (cellRow[j].state == State.MINE) {
                        fieldView[i][j] = State.MINE.symbol
                    }
                }
            }
            showMineField()
            println("You stepped on a mine and failed!")
            // ...and, game over
            return false
        }

        showMineField()
        return true
    }

    fun win() = println("Congratulations! You found all the mines!")
}