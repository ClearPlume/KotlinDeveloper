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
        0. Exit
    """.trimIndent()
    )

    print("Your choice:")
    val scanner = Scanner(System.`in`)
    var choice = scanner.nextInt()

    while (choice !in 0..3) {
        println("Invalid choice, try again!")
        choice = scanner.nextInt()
    }

    return choice
}

private class Matrix(private val row: Int, private val col: Int) {
    private val data: Array<Array<Number>> = Array(row) { Array(col) { -1L } }

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
                if (it.matches(Regex("\\d+"))) {
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

private operator fun Number.plus(other: Number): Number {
    return if (other is Long) {
        this as Long

        this + other
    } else {
        other as Double

        this.toDouble() + other
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
