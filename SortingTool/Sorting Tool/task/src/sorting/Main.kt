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

fun MutableList<Pair<SortingElement, Int>>.find(element: SortingElement): Pair<SortingElement, Int>? {
    var pair: Pair<SortingElement, Int>? = null

    for (item in this) {
        if (item.first == element) {
            pair = item
            break
        }
    }

    return pair
}

operator fun MutableList<Pair<SortingElement, Int>>.get(element: SortingElement): Int {
    return find(element)?.second ?: 0
}

operator fun MutableList<Pair<SortingElement, Int>>.set(element: SortingElement, num: Int) {
    val pair = find(element)

    if (pair != null) {
        remove(pair)
        add(element to num)
    } else {
        add(element to 1)
    }
}

class SortingTool(args: Array<String>) {
    private val scanner = Scanner(System.`in`)
    private val type = args.get("dataType", "word")
    private val sort = args.get("sortingType", "natural")
    private val data = mutableListOf<SortingElement>()
    private val dataSorted = mutableListOf<Pair<SortingElement, Int>>()

    private val total: Int

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
    }

    fun info() {
        println(
            "Total ${
                when (type) {
                    "long" -> "numbers"
                    "line" -> "lines"
                    else -> "words"
                }
            }: $total."
        )

        when (sort) {
            "natural" -> println("Sorted data: ${data.joinToString(if (type == "line") "\n" else " ")}")
            "byCount" -> for (item in dataSorted) {
                println("${item.first}: ${item.second} time(s), ${((item.second.toDouble() / total) * 100).toInt()}%")
            }
        }
    }

    private fun sort() {
        when (sort) {
            "natural" -> quickSort0(0, data.lastIndex)
            "byCount" -> {
                for (item in data) {
                    dataSorted[item] = dataSorted[item] + 1
                }

                dataSorted.sortWith(compareBy({ it.second }, { it.first }))
            }
        }
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
}

abstract class SortingElement(open val ele: Any) : Comparable<SortingElement>

class Long(override val ele: kotlin.Long) : SortingElement(ele) {
    override fun compareTo(other: SortingElement): Int {
        other as Long
        return ele.compareTo(other.ele)
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
        return ele.compareTo(other.ele)
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
        return ele.compareTo(other.ele)
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
