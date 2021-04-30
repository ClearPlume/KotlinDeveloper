package sorting

import java.util.*

fun main(args: Array<String>) {
    // write your code here
    val sortingTool = SortingTool(args)
    sortingTool.info()
}

fun Array<String>.get(name: String, missingArgValue: String): String {
    return (indexOf("-$name")).let { if (it != -1) this[it + 1] else missingArgValue }
}

fun String.isNumeric(): Boolean {
    for (c in this) {
        if (!c.isDigit() && c != '-') return false
    }

    return true
}

class SortingTool(args: Array<String>) {
    private val scanner = Scanner(System.`in`)
    private val data = mutableListOf<SortingElement>()
    private val type = args.get("dataType", "word")
    private val sort = "-sortIntegers" in args

    private var total: Int
    private var first: SortingElement
    private var firstCount: Int
    private var percent: Int

    init {
        when (type) {
            "long" -> {
                while (scanner.hasNextLong()) {
                    data.add(Long(scanner.nextLong()))
                }
            }
            "line" -> {
                while (scanner.hasNextLine()) {
                    data.add(Line(scanner.nextLine()))
                }
            }
            "word" -> {
                // \\S: All non blank characters
                // +: At least one character
                while (scanner.hasNext("\\S+")) {
                    data.add(Word(scanner.next("\\S+")))
                }
            }
        }

        sort()
        total = data.size
        first = data.last()
        firstCount = maxCount(first)
        percent = ((firstCount.toDouble() / total) * 100).toInt()
    }

    fun info() {
        when (type) {
            "long" -> {
                println(
                    """
                    Total numbers: $total.
                    ${
                        if (sort) {
                            "Sorted data: ${data.joinToString(" ")}"
                        } else {
                            "The greatest number: $first ($firstCount time(s), $percent%)."
                        }
                    }
                """.trimIndent()
                )
            }
            "line" -> {
                println(
                    """
                    Total lines: $total.
                    ${
                        if (sort) {
                            "Sorted data: ${data.joinToString(" ")}"
                        } else {
                            """
                                The longest line:
                                $first
                                ($firstCount times(s), $percent%).
                            """.trimIndent()
                        }
                    }
                """.trimIndent()
                )
            }
            "word" -> {
                println(
                    """
                    Total words: $total.
                    ${
                        if (sort) {
                            "Sorted data: ${data.joinToString(" ")}"
                        } else {
                            "The longest word: $first ($firstCount time(s), $percent%)."
                        }
                    }
                """.trimIndent()
                )
            }
        }
    }

    private fun sort() {
        quickSort0(0, data.lastIndex)
    }

    private fun quickSort0(left: Int, right: Int) {
        if (right <= left) return

        var pivot = right
        var cursor = left
        var direction = true

        do {
            if (direction) {
                if (data[cursor] > data[pivot]) {
                    data[cursor] = data[pivot].also { data[pivot] = data[cursor] }
                    cursor = pivot.also { pivot = cursor }

                    direction = !direction
                }
            } else {
                if (data[cursor] < data[pivot]) {
                    data[cursor] = data[pivot].also { data[pivot] = data[cursor] }
                    cursor = pivot.also { pivot = cursor }

                    direction = !direction
                }
            }

            cursor += if (direction) 1 else -1
        } while (cursor != pivot)

        quickSort0(left, pivot - 1)
        quickSort0(pivot + 1, right)
    }

    private fun maxCount(max: SortingElement): Int {
        var count = 0

        for (item in data) {
            if (item == max) {
                count++
            }
        }

        return count
    }
}

abstract class SortingElement(open val ele: Any) : Comparable<SortingElement>

class Long(override val ele: kotlin.Long) : SortingElement(ele) {
    override fun compareTo(other: SortingElement): Int {
        other as Long
        return if (ele < other.ele) -1 else if (ele == other.ele) 0 else 1
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Long

        if (ele != other.ele) return false

        return true
    }

    override fun hashCode(): Int {
        return ele.hashCode()
    }

    override fun toString(): String {
        return ele.toString()
    }
}

class Line(override val ele: String) : SortingElement(ele) {
    override fun compareTo(other: SortingElement): Int {
        other as Line
        return ele.length - other.ele.length
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Line

        if (ele != other.ele) return false

        return true
    }

    override fun hashCode(): Int {
        return ele.hashCode()
    }

    override fun toString(): String {
        return ele
    }
}

class Word(override val ele: String) : SortingElement(ele) {
    override fun compareTo(other: SortingElement): Int {
        other as Word
        return if (!ele.isNumeric()) {
            ele.length - other.ele.length
        } else {
            val numSort = ele.toLong() - other.ele.toLong()
            if (numSort > 0) 1 else if (numSort < 0) -1 else 0
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Word

        if (ele != other.ele) return false

        return true
    }

    override fun hashCode(): Int {
        return ele.hashCode()
    }

    override fun toString(): String {
        return ele
    }
}
