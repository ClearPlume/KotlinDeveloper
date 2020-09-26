package minesweeper

import kotlin.random.Random

class MineField(private val fieldSize: Int, private val mineNum: Int) {
    private val field: Array<CharArray> = Array(fieldSize) { CharArray(fieldSize) { '.' } }

    fun initial() {
        var curMineNum = 0

        while (curMineNum < mineNum) {
            val mineRow = Random.nextInt(fieldSize)
            val mineCol = Random.nextInt(fieldSize)

            if (field[mineRow][mineCol] != 'X') {
                field[mineRow][mineCol] = 'X'
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
                        if (field[x - 1][y - 1] == 'X') {
                            aroundMines++
                        }
                    }
                    if (y - 1 >= 0) {
                        if (field[x][y - 1] == 'X') {
                            aroundMines++
                        }
                    }
                    if (x + 1 <= field[0].lastIndex && y - 1 >= 0) {
                        if (field[x + 1][y - 1] == 'X') {
                            aroundMines++
                        }
                    }
                    if (x - 1 >= 0) {
                        if (field[x - 1][y] == 'X') {
                            aroundMines++
                        }
                    }
                    if (x + 1 <= field[0].lastIndex) {
                        if (field[x + 1][y] == 'X') {
                            aroundMines++
                        }
                    }
                    if (x - 1 >= 0 && y + 1 <= field.lastIndex) {
                        if (field[x - 1][y + 1] == 'X') {
                            aroundMines++
                        }
                    }
                    if (y + 1 <= field.lastIndex) {
                        if (field[x][y + 1] == 'X') {
                            aroundMines++
                        }
                    }
                    if (x + 1 <= field[0].lastIndex && y + 1 <= field.lastIndex) {
                        if (field[x + 1][y + 1] == 'X') {
                            aroundMines++
                        }
                    }
                    if (aroundMines > 0) {
                        field[x][y] = '0' + aroundMines
                    }
                }
            }
        }
    }

    fun showMineField() {
        for (f in field) {
            println(f)
        }
    }
}