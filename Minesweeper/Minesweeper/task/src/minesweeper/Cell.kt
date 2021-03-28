package minesweeper

/**
 * Represents a cell in the game
 *
 * @param x 单元格的x坐标
 * @param y 单元格的y坐标
 */
class Cell(var x: Int = 0, var y: Int = 0, var state: State = State.UN_EXPLORED) {
    constructor(cell: Cell, state: State): this() {
        this.x = cell.x
        this.y = cell.y
        this.state = state
    }

    /**
     * Symbols of cell
     */
    enum class State(val symbol: Char) {
        UN_EXPLORED('.'),
        EXPLORED_FREE('/'),
        MINE('X'),
        MARKED_MINE('*');

        override fun toString(): String {
            return "state = $symbol"
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Cell

        if (x != other.x) return false
        if (y != other.y) return false
        if (state != other.state) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        result = 31 * result + state.hashCode()
        return result
    }

    override fun toString(): String {
        return "x = $x, y = $y, state = $state"
    }
}