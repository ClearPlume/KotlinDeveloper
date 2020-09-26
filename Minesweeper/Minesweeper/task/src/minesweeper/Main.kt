package minesweeper

import java.util.*

fun main() {
    print("How many mines do you want on the field?")
    val mainField = MineField(9, Scanner(System.`in`).nextInt())
    mainField.initial()
    mainField.calcMineNum()
    mainField.showMineField()
}