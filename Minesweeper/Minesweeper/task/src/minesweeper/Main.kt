package minesweeper

fun main() {
    print("How many mines do you want on the field?")

    val fieldSize = 9
    val mineField = MineField(fieldSize, readLine()!!.toInt())

    mineField.showMineField()

    do {
        print("Set/unset mines marks or claim a cell as free:")

        val playerInput = readLine()!!.split(' ')
        val cell = Cell(playerInput[0].toInt() - 1, playerInput[1].toInt() - 1)
        val input = playerInput[2]

        if (input == "mine" || input == "free") {
            mineField.action(cell, input)
        } else {
            println("invalid input: \"$input\", Legal options are: [\"mine\", \"free\"] ")
        }
    } while (!mineField.checkGameState())
}