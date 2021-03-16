package minesweeper

fun main() {
    print("How many mines do you want on the field?")

    val fieldSize = 9
    val mineField = MineField(fieldSize, readLine()!!.toInt())

    mineField.calcMineNum()
    mineField.showMineField()

    while (mineField.running) {
        print("Set/unset mines marks or claim a cell as free:")

        val playerInput = readLine()!!.split(' ')
        val x = playerInput[0].toInt() - 1
        val y = playerInput[1].toInt() - 1

        val cellSymbol = mineField.getCellSymbol(x, y)

        if (cellSymbol.isDigit()) {
            println("There is a number here!")
            continue
        } else {
            if ("mine" == playerInput[2]) {

            } else {

            }
            mineField.showMineField()
        }
    }

    if (mineField.isWin()) {
        mineField.win()
    } else {
        mineField.failed()
    }
}