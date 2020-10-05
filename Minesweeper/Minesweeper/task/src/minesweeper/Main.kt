package minesweeper

fun main() {
    print("How many mines do you want on the field?")

    val fieldSize = 9
    val mineField = MineField(fieldSize, readLine()!!.toInt())

    mineField.initial()
    mineField.calcMineNum()
    mineField.showMineField()

    while (!mineField.isWin()) {
        print("Set/delete mine marks (x and y coordinates):")
        val playerInput = readLine()!!
        val x = playerInput.split(' ')[0].toInt() - 1
        val y = playerInput.split(' ')[1].toInt() - 1

        val cellSymbol = mineField.getCellSymbol(x, y)

        when {
            cellSymbol.isDigit() -> {
                println("There is a number here!")
                continue
            }
            cellSymbol == '.' -> {
                mineField.setCellSymbol(x, y, '*')
                mineField.showMineField()
            }
            else -> {
                mineField.setCellSymbol(x, y, '.')
                mineField.showMineField()
            }
        }
    }

    mineField.win()
}