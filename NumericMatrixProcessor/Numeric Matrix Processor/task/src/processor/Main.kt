package processor

import java.util.*

fun main() {
    var choice = menu()

    while (choice != 0) {
        when (choice) {
            1 -> {
                print("Enter size of first matrix:")
                val (m1r, m1c) = readLine()!!.split(" ").map { it.toInt() }
                val m1 = Matrix(m1r, m1c)
                println("Enter first matrix:")
                m1.readFromConsole()

                print("Enter size of second matrix:")
                val (m2r, m2c) = readLine()!!.split(" ").map { it.toInt() }
                val m2 = Matrix(m2r, m2c)
                println("Enter first matrix:")
                m2.readFromConsole()

                val ms = m1 + m2
                if (ms != null) {
                    println("The result is:")
                    println(ms)
                } else {
                    println("The operation cannot be performed.")
                }
            }
            2 -> {
                print("Enter size of first matrix: ")
                val (m1r, m1c) = readLine()!!.split(" ").map { it.toInt() }
                val m1 = Matrix(m1r, m1c)
                println("Enter first matrix:")
                m1.readFromConsole()

                print("Enter constant:")
                val cStr = readLine()!!
                val c: Number = if (cStr.matches(Regex("\\d+"))) cStr.toLong() else cStr.toDouble()

                val ms = m1 * c
                println("The result is:")
                println(ms)
            }
            3 -> {
                print("Enter size of first matrix:")
                val (m1r, m1c) = readLine()!!.split(" ").map { it.toInt() }
                val m1 = Matrix(m1r, m1c)
                println("Enter first matrix:")
                m1.readFromConsole()

                print("Enter size of second matrix:")
                val (m2r, m2c) = readLine()!!.split(" ").map { it.toInt() }
                val m2 = Matrix(m2r, m2c)
                println("Enter second matrix:")
                m2.readFromConsole()

                val ms = m1 * m2
                if (ms != null) {
                    println("The result is:")
                    println(ms)
                } else {
                    println("The operation cannot be performed.")
                }
            }
            4 -> {
                val type = transposeMenu()

                print("Enter size of matrix: ")
                val (m1r, m1c) = readLine()!!.split(" ").map { it.toInt() }
                val m1 = Matrix(m1r, m1c)
                println("Enter matrix:")
                m1.readFromConsole()

                m1.transpose(type)
                println(m1)
            }
            5 -> {
                print("Enter size of matrix: ")
                val (m1r, m1c) = readLine()!!.split(" ").map { it.toInt() }
                val m1 = Matrix(m1r, m1c)
                println("Enter matrix:")
                m1.readFromConsole()
                println("The result is:")
                println(m1.determinant())
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

        return data.minor()
    }
}

private enum class MatrixTransposeType {
    MAIN, SIDE, VERTICAL, HORIZONTAL;

    companion object {
        fun valueOf(type: Int): MatrixTransposeType = values()[type - 1]
    }
}

private val <T> Array<Array<T>>.rows: Int
    get() = size

private val <T> Array<Array<T>>.cols: Int
    get() = this[0].size

private fun <T> Array<Array<T>>.copy(other: Array<Array<T>>) {
    for (r in 0 until rows) {
        for (c in 0 until cols) {
            this[r][c] = other[r][c]
        }
    }
}

private fun <T> Array<Array<T>>.minor(): Number {
    var result: Number

    for (i in this[0].indices) {

    }

    TODO()
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
