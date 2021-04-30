package sorting

import java.util.*

fun main(args: Array<String>) {
    // write your code here
    val sortingTool = SortingTool(args.get("dataType", "word"))
    sortingTool.info()
}

fun Array<String>.get(name: String, missingArgValue: String): String {
    return (indexOf("-$name")).let { if (it != -1) this[it + 1] else missingArgValue }
}

class SortingTool(private val type: String) {
    private val scanner = Scanner(System.`in`)
    private val data = mutableListOf<SortingElement>()

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

        total = data.size
        first = max()
        firstCount = maxCount(first)
        percent = ((firstCount.toDouble() / total) * 100).toInt()
    }

    fun info() {
        when (type) {
            "long" -> {
                println(
                    """
                    Total numbers: $total.
                    The greatest number: $first ($firstCount time(s), $percent%).
                """.trimIndent()
                )
            }
            "line" -> {
                println(
                    """
                    Total lines: $total.
                    The longest line:
                    $first
                    ($firstCount time(s), $percent%).
                """.trimIndent()
                )
            }
            "word" -> {
                println(
                    """
                    Total words: $total.
                    The longest word: $first ($firstCount time(s), $percent%).
                """.trimIndent()
                )
            }
        }
    }

    private fun max(): SortingElement {
        var max = data[0]

        for (i in 1..data.lastIndex) {
            if (max < data[i]) {
                max = data[i]
            }
        }

        return max
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
        return ele.length - other.ele.length
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
