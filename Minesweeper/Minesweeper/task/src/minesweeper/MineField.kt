package minesweeper

import minesweeper.Cell.State
import kotlin.random.Random

class MineField(private val fieldSize: Int, mineNum: Int) {
    private var firstStep = true

    // Data used as cell display
    private val fieldView: Array<CharArray> = Array(fieldSize) { CharArray(fieldSize) { State.UN_EXPLORED.symbol } }

    // Cells in the game, it's background data for logic things
    private val cells: Array<Array<Cell>> = Array(fieldSize) { y ->
        Array(fieldSize) { x ->
            Cell(x, y)
        }
    }

    // Mines in the game, omit the process of getting mines from cells
    private val mines: Array<Cell> = Array(mineNum) { Cell(state = State.MINE) }

    // Did the player step on a mine
    private var steppedMine: Boolean = false

    init {
        // Number of Mines currently generated
        var curMineNum = 0

        while (curMineNum < mineNum) {
            val x = Random.nextInt(fieldSize)
            val y = Random.nextInt(fieldSize)

            if (cells[y][x].state != State.MINE) {
                cells[y][x].state = State.MINE
                mines[curMineNum].x = x
                mines[curMineNum].y = y

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
            if (cellSymbol == State.MARKED_MINE.symbol) {
                fieldView[cell.y][cell.x] = State.UN_EXPLORED.symbol
            } else {
                fieldView[cell.y][cell.x] = State.MARKED_MINE.symbol
            }
        } else {
            if (firstStep) {
                firstStep = false

                // Make sure that the first cell explored with the free command is not a mine
                if (Cell(cell, State.MINE) in mines) {
                    var noneMineCell = findCellNotMines()

                    if (null == noneMineCell) {
                        lose()
                        return
                    }

                    cells[cell.y][cell.x] = noneMineCell.also { noneMineCell = cells[cell.y][cell.x] }
                }
            }

            // Player thinks the cell is empty
            val cellData = cells[cell.y][cell.x]

            if (State.MINE == cellData.state) {
                // The player stepped on the mine
                steppedMine = true
                return
            }

            // If that cell doesn't have mines, then mark it as "explored blank cell"
            if (fieldView[cell.y][cell.x] != State.MARKED_MINE.symbol) {
                fieldView[cell.y][cell.x] = State.EXPLORED_FREE.symbol
            }

            // This cell has no mines, explore it
            exploreCell(cellData)
        }
    }

    private fun findCellNotMines(): Cell? {
        var noneMineCell: Cell? = null

        for (row in cells) {
            for (cell in row) {
                if (cell.state == State.MINE) {
                    noneMineCell = cell
                    break
                }
            }
        }

        return noneMineCell
    }

    /**
     * Explore a cell. If there are no mines in this cell, judge whether there are mines around
     *
     *   Have:
     *     Only that cell is explored, revealing a number of mines around it
     *
     *   None:
     *     Continue to explore the mines around this cell
     */
    private fun exploreCell(cell: Cell) {
        val mineNumAroundCell = aroundCellHaveMine(cell)
        cells[cell.y][cell.x].state = State.EXPLORED_FREE
        fieldView[cell.y][cell.x] = State.EXPLORED_FREE.symbol

        if (mineNumAroundCell > 0) {
            // There are mines around this cell. Stop exploring, revealing a number of mines around it
            fieldView[cell.y][cell.x] = mineNumAroundCell.toString()[0]
            return
        }

        if (cell.x - 1 >= 0 && cell.y - 1 >= 0) {
            if (cells[cell.y - 1][cell.x - 1].state == State.UN_EXPLORED) {
                exploreCell(cells[cell.y - 1][cell.x - 1])
            }
        }
        if (cell.y - 1 >= 0) {
            if (cells[cell.y - 1][cell.x].state == State.UN_EXPLORED) {
                exploreCell(cells[cell.y - 1][cell.x])
            }
        }
        if (cell.x + 1 <= fieldView[0].lastIndex && cell.y - 1 >= 0) {
            if (cells[cell.y - 1][cell.x + 1].state == State.UN_EXPLORED) {
                exploreCell(cells[cell.y - 1][cell.x + 1])
            }
        }
        if (cell.x - 1 >= 0) {
            if (cells[cell.y][cell.x - 1].state == State.UN_EXPLORED) {
                exploreCell(cells[cell.y][cell.x - 1])
            }
        }
        if (cell.x + 1 <= fieldView[0].lastIndex) {
            if (cells[cell.y][cell.x + 1].state == State.UN_EXPLORED) {
                exploreCell(cells[cell.y][cell.x + 1])
            }
        }
        if (cell.x - 1 >= 0 && cell.y + 1 <= fieldView.lastIndex) {
            if (cells[cell.y + 1][cell.x - 1].state == State.UN_EXPLORED) {
                exploreCell(cells[cell.y + 1][cell.x - 1])
            }
        }
        if (cell.y + 1 <= fieldView.lastIndex) {
            if (cells[cell.y + 1][cell.x].state == State.UN_EXPLORED) {
                exploreCell(cells[cell.y + 1][cell.x])
            }
        }
        if (cell.x + 1 <= fieldView[0].lastIndex && cell.y + 1 <= fieldView.lastIndex) {
            if (cells[cell.y + 1][cell.x + 1].state == State.UN_EXPLORED) {
                exploreCell(cells[cell.y + 1][cell.x + 1])
            }
        }
    }

    /**
     * Determine how many mines around the cell
     */
    private fun aroundCellHaveMine(cell: Cell): Int {
        var aroundMines = 0

        if (cell.x - 1 >= 0 && cell.y - 1 >= 0) {
            if (cells[cell.y - 1][cell.x - 1].state == State.MINE) {
                aroundMines++
            }
        }
        if (cell.y - 1 >= 0) {
            if (cells[cell.y - 1][cell.x].state == State.MINE) {
                aroundMines++
            }
        }
        if (cell.x + 1 <= fieldView[0].lastIndex && cell.y - 1 >= 0) {
            if (cells[cell.y - 1][cell.x + 1].state == State.MINE) {
                aroundMines++
            }
        }
        if (cell.x - 1 >= 0) {
            if (cells[cell.y][cell.x - 1].state == State.MINE) {
                aroundMines++
            }
        }
        if (cell.x + 1 <= fieldView[0].lastIndex) {
            if (cells[cell.y][cell.x + 1].state == State.MINE) {
                aroundMines++
            }
        }
        if (cell.x - 1 >= 0 && cell.y + 1 <= fieldView.lastIndex) {
            if (cells[cell.y + 1][cell.x - 1].state == State.MINE) {
                aroundMines++
            }
        }
        if (cell.y + 1 <= fieldView.lastIndex) {
            if (cells[cell.y + 1][cell.x].state == State.MINE) {
                aroundMines++
            }
        }
        if (cell.x + 1 <= fieldView[0].lastIndex && cell.y + 1 <= fieldView.lastIndex) {
            if (cells[cell.y + 1][cell.x + 1].state == State.MINE) {
                aroundMines++
            }
        }
        return aroundMines
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
            lose()
            // ...and, game over
            return true
        }

        // Check whether the player has marked all mines or all free cells
        if (checkPlayerMarkMines() || checkPlayerMarkFree()) {
            showMineField()
            win()
            return true
        }

        showMineField()
        return false
    }

    private fun checkPlayerMarkMines(): Boolean {
        for (mine in mines) {
            if (fieldView[mine.y][mine.x] != State.MARKED_MINE.symbol) {
                return false
            }
        }
        return true
    }

    private fun checkPlayerMarkFree(): Boolean {
        for (y in fieldView.indices) {
            for (x in fieldView[0].indices) {
                if (Cell(x, y, State.MINE) !in mines) {
                    if (fieldView[y][x] != State.EXPLORED_FREE.symbol && !fieldView[y][x].isDigit()) {
                        return false
                    }
                }
            }
        }
        return true
    }

    private fun win() = println("Congratulations! You found all the mines!")

    private fun lose() = println("You stepped on a mine and failed!")
}