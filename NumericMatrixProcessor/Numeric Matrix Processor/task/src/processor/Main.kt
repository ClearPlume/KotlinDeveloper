package processor

import java.util.*

val orders = mapOf(0 to "first", 1 to "second", 2 to "third", 3 to "fourth", 4 to "fifth")

fun main() {
    var choice = menu()

    while (choice != 0) {
        when (choice) {
            1 -> {
                val (m1, m2) = readMatrices()

                with(m1 + m2) {
                    if (this != null) {
                        println("The result is:")
                        println(this)
                    } else {
                        println("The operation cannot be performed.")
                    }
                }
            }
            2 -> {
                val m = readMatrix()
                print("Enter constant:")
                val cStr = readLine()!!
                val c: Number = if (cStr.matches(Regex("\\d+"))) cStr.toLong() else cStr.toDouble()

                val ms = m * c
                println("The result is:")
                println(ms)
            }
            3 -> {
                val (m1, m2) = readMatrices()

                with(m1 * m2) {
                    if (this != null) {
                        println("The result is:")
                        println(this)
                    } else {
                        println("The operation cannot be performed.")
                    }
                }
            }
            4 -> {
                val type = transposeMenu()
                val m = readMatrix()

                m.transpose(type)
                println(m)
            }
            5 -> {
                val m = readMatrix()
                println("The result is:")
                println(m.determinant())
            }
        }
        choice = menu()
    }
}

private fun menu(): Int {
    println(
        """
        1. Add matrices
        2. Multiply matrix by a constant
        3. Multiply matrices
        4. Transpose matrix
        5. Calculate a determinant
        0. Exit
    """.trimIndent()
    )

    print("Your choice:")
    val scanner = Scanner(System.`in`)
    var choice = scanner.nextInt()

    while (choice !in 0..5) {
        println("Invalid choice, try again!")
        choice = scanner.nextInt()
    }

    return choice
}

private fun transposeMenu(): Int {
    println(
        """
        1. Main diagonal
        2. Side diagonal
        3. Vertical line
        4. Horizontal line
    """.trimIndent()
    )

    print("Your choice:")
    val scanner = Scanner(System.`in`)
    var choice = scanner.nextInt()

    while (choice !in 1..4) {
        println("Invalid choice, try again!")
        choice = scanner.nextInt()
    }

    return choice
}

private fun readMatrix(): Matrix {
    print("Enter size of matrix: ")
    val (mr, mc) = readLine()!!.split(" ").map { it.toInt() }
    val m = Matrix(mr, mc)
    println("Enter matrix:")
    m.readFromConsole()
    return m
}

private fun readMatrices(num: Int = 2): List<Matrix> {
    val matrices = mutableListOf<Matrix>()

    for (i in 0 until num) {
        print("Enter ${orders[i]} size of matrix: ")
        val (mr, mc) = readLine()!!.split(" ").map { it.toInt() }

        println("Enter ${orders[i]} matrix:")
        val m = Matrix(mr, mc)
        m.readFromConsole()
        matrices.add(m)
    }

    return matrices
}

private class Matrix(private val row: Int, private val col: Int) {
    private var data: Array<Array<Number>> = Array(row) { Array(col) { -1L } }

    constructor(row: Int, col: Int, data: Array<Array<Number>>) : this(row, col) {
        this.data.copy(data)
    }

    operator fun plus(other: Matrix): Matrix? {
        if (row != other.row || col != other.col) return null

        val dataSum: Array<Array<Number>> = Array(row) { Array(col) { -1L } }

        for (r in 0 until row) {
            for (c in 0 until col) {
                dataSum[r][c] = data[r][c] + other.data[r][c]
            }
        }

        return Matrix(row, col, dataSum)
    }

    operator fun times(c: Number): Matrix {
        val dataPro: Array<Array<Number>> = Array(row) { Array(col) { -1L } }

        for (r in 0 until row) {
            for (_c in 0 until col) {
                dataPro[r][_c] = data[r][_c] * c
            }
        }

        return Matrix(row, col, dataPro)
    }

    operator fun times(other: Matrix): Matrix? {
        if (col != other.row) return null

        val dataPro: Array<Array<Number>> = Array(row) { Array(other.col) { -1L } }

        for (r in 0 until row) {
            for (c in 0 until other.col) {
                var result: Number = 0L
                var or = 0

                for (a in data[r]) {
                    result += a * other.data[or++][c]
                }

                dataPro[r][c] = result
            }
        }

        return Matrix(row, other.col, dataPro)
    }

    operator fun get(index: Int): Array<Number> {
        val col = Array<Number>(row) { -1 }

        for (i in 0 until row) {
            col[i] = data[i][index]
        }

        return col
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
            val cols = readLine()!!.split(" ").map {
                if (it.matches(Regex("-?\\d+"))) {
                    it.toLong()
                } else {
                    it.toDouble()
                }
            }

            for (c in 0 until col) {
                data[r][c] = cols[c]
            }
        }
    }

    fun transpose(type: Int) {
        data = when (MatrixTransposeType.valueOf(type)) {
            MatrixTransposeType.MAIN -> {
                val new: Array<Array<Number>> = Array(col) { Array(row) { -1L } }

                new.also {
                    it.forEachIndexed { r, rows ->
                        rows.forEachIndexed { c, _ ->
                            it[r][c] = data[c][r]
                        }
                    }
                }
            }
            MatrixTransposeType.SIDE -> {
                val new: Array<Array<Number>> = Array(col) { Array(row) { -1L } }
                val reversedRowIndices = (row - 1 downTo 0).toList()

                new.also {
                    for (r in new.indices) {
                        it[r] = this[reversedRowIndices[r]].reversedArray()
                    }
                }
            }
            MatrixTransposeType.VERTICAL -> {
                val new: Array<Array<Number>> = Array(row) { Array(col) { -1L } }

                new.also {
                    it.forEachIndexed { r, rows ->
                        val reversedColIndices = (row - 1 downTo 0).toList()
                        rows.forEachIndexed { c, _ ->
                            it[r][c] = data[r][reversedColIndices[c]]
                        }
                    }
                }
            }
            MatrixTransposeType.HORIZONTAL -> {
                val new: Array<Array<Number>> = Array(row) { Array(col) { -1L } }
                val reversedRowIndices = (row - 1 downTo 0).toList()

                new.also {
                    for (r in new.indices) {
                        it[r] = data[reversedRowIndices[r]]
                    }
                }
            }
        }
    }

    fun determinant(): Number {
        return calcDeterminant(data)
    }

    fun calcDeterminant(data: Array<Array<Number>>): Number {
        if (data.size == 2) {
            return data[0][0] * data[1][1] - data[0][1] * data[1][0]
        }

        var cofactor: Number = if (data[0][0] is Long) 0L else 0.0

        val row = data[0]
        for (i in row.indices) {
            if (row[i] != 0) {
                cofactor += calcDeterminant(data.minor(0, i)) * if (i % 2 == 0) row[i] else -row[i]
            }
        }

        return cofactor
    }
}

private enum class MatrixTransposeType {
    MAIN, SIDE, VERTICAL, HORIZONTAL;

    companion object {
        fun valueOf(type: Int): MatrixTransposeType = values()[type - 1]
    }
}

private val Array<Array<Number>>.rows: Int
    get() = size

private val Array<Array<Number>>.cols: Int
    get() = this[0].size

private fun Array<Array<Number>>.copy(other: Array<Array<Number>>) {
    for (r in 0 until rows) {
        for (c in 0 until cols) {
            this[r][c] = other[r][c]
        }
    }
}

private fun Array<Array<Number>>.minor(x: Int, y: Int): Array<Array<Number>> {
    val result = mutableListOf<Array<Number>>()
    val filteredRows = filterIndexed { r, _ -> r != x }

    filteredRows.forEach {
        val filteredCols = it.filterIndexed { c, _ -> c != y }.toTypedArray()
        result.add(filteredCols)
    }

    return result.toTypedArray()
}

private operator fun Number.plus(other: Number): Number {
    return if (other is Long) {
        this as Long

        this + other
    } else {
        other as Double

        this.toDouble() + other
    }
}

private operator fun Number.minus(other: Number): Number {
    return if (other is Long) {
        this as Long

        this - other
    } else {
        other as Double

        this.toDouble() - other
    }
}

private operator fun Number.times(other: Number): Number {
    return if (other is Long) {
        this as Long

        this * other
    } else {
        other as Double

        this.toDouble() * other
    }
}

private operator fun Number.unaryMinus(): Number {
    return if (this is Long) {
        this * -1
    } else {
        this.toDouble() * -1
    }
}
