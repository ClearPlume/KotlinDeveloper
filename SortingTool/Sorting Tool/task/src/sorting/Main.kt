package sorting

import java.util.*
import kotlin.math.roundToInt
import kotlin.system.exitProcess

val commandList = arrayOf("sortingType", "dataType")

fun main(args: Array<String>) {
    // write your code here
    val argMap = args.parse()
    val sortingTool = SortingTool(argMap)
    sortingTool.info()
}

fun Array<String>.parse(): Map<String, String> {
    val args = mutableMapOf<String, String>()

    for (i in indices) {
        if (this[i].startsWith('-')) {
            if (this[i].substring(1) in commandList) {
                if (i < lastIndex && !this[i + 1].startsWith('-')) {
                    args[this[i].substring(1)] = this[i + 1]
                }
            } else {
                println("\"${this[i]}\" is not a valid parameter. It will be skipped.")
            }
        }
    }

    return args
}

fun Array<String>.get(name: String): String? {
    var arg: String? = null

    for (i in this.indices) {
        if (this[i].startsWith('-')) {
            if ("-$name" == this[i]) {
                if (!this[i + 1].startsWith('-')) {
                    arg = this[i + 1]
                    break
                }
            }
        }
    }

    return arg
}

fun String.isNumeric(): Boolean {
    for (c in this) {
        if (!c.isDigit() && c != '-') {
            return false
        }
    }

    return true
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

class SortingTool(args: Map<String, String>) {
    private val scanner = Scanner(System.`in`)
    private val type = args["dataType"] ?: "word"
    private val sort = args["sortingType"] ?: "natural"
    private val data = mutableListOf<SortingElement>()
    private val dataSorted = mutableListOf<Pair<SortingElement, Int>>()

    private val total: Int

    init {
        if (type !in arrayOf("long", "word", "line")) {
            println("No data type defined!")
            exitProcess(0)
        }

        if (sort !in arrayOf("natural", "byCount")) {
            println("No sorting type defined!")
            exitProcess(0)
        }

        if (type != "line") {
            // \\S: All non blank characters
            // +: At least one character
            while (scanner.hasNext("\\S+")) {
                val next = scanner.next("\\S+")
                when (type) {
                    "long" -> {
                        if (next.isNumeric()) {
                            data.add(Long(next.toLong()))
                        } else {
                            println("\"$next\" is not a long. It will be skipped.")
                        }
                    }
                    "word" -> data.add(Word(next))
                }
            }
        } else {
            while (scanner.hasNextLine()) {
                data.add(Line(scanner.nextLine()))
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
                println("${item.first}: ${item.second} time(s), ${((item.second.toDouble() / total) * 100).roundToInt()}%")
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
