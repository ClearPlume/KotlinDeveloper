package minesweeper

import java.util.*
import kotlin.random.Random

fun main() {
    print("How many mines do you want on the field?")
    val numOfMines = Scanner(System.`in`).nextInt()
    var curMineNum = 0
    val field = Array(9) { CharArray(9) { '.' } }

    while (curMineNum < numOfMines) {
        val mineRow = Random.nextInt(9)
        val mineCol = Random.nextInt(9)

        if (field[mineRow][mineCol] != 'X') {
            field[mineRow][mineCol] = 'X'
            curMineNum++
        }
    }

    for (f in field) {
        println(f)
    }
}