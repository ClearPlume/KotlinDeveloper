package minesweeper

/**
 * Represents a cell in the game
 *
 * @param x 单元格的x坐标
 * @param y 单元格的y坐标
 */
class Cell(var x: Int = 0, var y: Int = 0, var state: State = State.UN_EXPLORED) {
    /**
     * Symbols of cell
     */
    enum class State(val symbol: Char) {
        UN_EXPLORED('.'),
        EXPLORED_FREE('/'),
        MINE('X'),
        MARKED_MINE('*')
    }

    /**
     * Judge whether they are equal according to the coordinate position (x, y)
     *
     * @param other Another cell
     */
    override fun equals(other: Any?): Boolean {
        val oth = other as Cell
        return x == oth.x && y == oth.y
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }

    override fun toString(): String {
        return "x = $x, y = $y"
    }
}