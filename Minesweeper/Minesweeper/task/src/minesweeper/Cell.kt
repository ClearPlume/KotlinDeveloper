package minesweeper

class Cell(var x: Int = 0, var y: Int = 0) {
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