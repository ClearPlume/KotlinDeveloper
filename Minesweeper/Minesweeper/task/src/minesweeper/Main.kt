package minesweeper

fun main() {
    print("How many mines do you want on the field?")

    val fieldSize = 9
    val mineField = MineField(fieldSize, readLine()!!.toInt())

    mineField.initial()
    mineField.calcMineNum()
    mineField.showMineField()

    while (!mineField.isWin()) {
        print("Set/unset mines marks or claim a cell as free:")
        val playerInputOriginal = readLine()!!
        val playerInput = playerInputOriginal.split(' ')
        val x = playerInput[0].toInt() - 1
        val y = playerInput[1].toInt() - 1

        val cellSymbol = mineField.getCellSymbol(x, y)

        if (cellSymbol.isDigit()) {
            println("There is a number here!")
            continue
        } else {
            when (playerInput[2]) {
                "mine" -> {
                    when (cellSymbol) {
                        '.' -> {
                            mineField.setCellSymbol(x, y, '*')
                            mineField.showMineField()
                        }
                        else -> {
                            mineField.setCellSymbol(x, y, '.')
                            mineField.showMineField()
                        }
                    }
                }
                "free" -> {
                    if (cellSymbol == '.') {
                        mineField.setCellSymbol(x, y, '/')
                        mineField.showMineField()
                    }
                }
                else -> {
                    println("Error command!")
                }
            }
        }
    }

    mineField.win()
}