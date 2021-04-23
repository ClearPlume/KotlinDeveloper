package processor

private val <T> Array<Array<T>>.rows: Int
    get() = size

private val <T> Array<Array<T>>.cols: Int
    get() = this[0].size

fun main() {
    val (m1r, m1c) = readLine()!!.split(" ").map { it.toInt() }
    val m1 = Matrix(m1r, m1c)
    m1.readFromConsole()

    val c = readLine()!!.toInt()

    val ms = m1 * c
    println(ms)
}

private fun <T> Array<Array<T>>.copy(other: Array<Array<T>>) {
    for (r in 0 until rows) {
        for (c in 0 until cols) {
            this[r][c] = other[r][c]
        }
    }
}

class Matrix(private val row: Int, private val col: Int) {
    private val data: Array<Array<Int>> = Array(row) { Array(col) { -1 } }

    constructor(row: Int, col: Int, data: Array<Array<Int>>) : this(row, col) {
        this.data.copy(data)
    }

    operator fun plus(other: Matrix): Matrix? {
        if (row != other.row || col != other.col) return null

        val dataSum = Array(row) { Array(col) { -1 } }

        for (r in 0 until row) {
            for (c in 0 until col) {
                dataSum[r][c] = data[r][c] + other.data[r][c]
            }
        }

        return Matrix(row, col, dataSum)
    }

    operator fun times(c: Int): Matrix {
        val dataPro = Array(row) { Array(col) { -1 } }

        for (r in 0 until row) {
            for (_c in 0 until col) {
                dataPro[r][_c] = data[r][_c] * c
            }
        }

        return Matrix(row, col, dataPro)
    }

    override fun toString(): String {
        val cols = mutableListOf<String>()

        for (colArr in data) {
            cols.add(colArr.joinToString(" "))
        }

        return cols.joinToString(System.lineSeparator())
    }

    fun readFromConsole() {
        for (r in 0 until row) {
            val cols = readLine()!!.split(" ").map { it.toInt() }

            for (c in 0 until col) {
                data[r][c] = cols[c]
            }
        }
    }
}
