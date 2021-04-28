package phonebook

import java.io.File
import java.util.*
import kotlin.math.sqrt

fun main() {
    val directory = readFile("E:\\directory.txt")
    val find = readFile("E:\\find.txt")

    val allFindEntryNum = find.size

    println("Start searching (linear search)...")

    val linearSearchResult = linearSearch(directory, find)

    printFoundResult(allFindEntryNum, linearSearchResult)

    println()
    println("Start searching (bubble sort + jump search)...")

    var sortResult = directory.bubbleSort(linearSearchResult.timeInMillis() * 10L)
    var searchResult = if (sortResult.succeed) {
        jumpSearch(sortResult.list!!, find)
    } else {
        linearSearch(directory, find)
    }
    var foundResult = sortResult + searchResult

    printFoundResult(allFindEntryNum, foundResult)
    printSortResult(sortResult)
    printSearchResult(searchResult)

    println()
    println("Start searching (quick sort + binary search)...")

    sortResult = directory.quickSort()
    searchResult = binarySearch(sortResult.list!!, find)
    foundResult = sortResult + searchResult

    printFoundResult(allFindEntryNum, foundResult)
    printSortResult(sortResult)
    printSearchResult(searchResult)
}

fun readFile(name: String): MutableList<String> {
    val fileList = mutableListOf<String>()

    File(name).forEachLine { fileList.add(it) }

    return fileList
}

fun linearSearch(source: MutableList<String>, find: MutableList<String>): Result {
    val result = Result()
    val startTime = System.currentTimeMillis()

    for (item in find) {
        if (source.find { item in it } != null) {
            result.entryNum++
        }
    }

    result.setTime(System.currentTimeMillis() - startTime)
    return result
}

fun MutableList<String>.bubbleSort(limitTime: Long): Result {
    val list = mutableListOf<String>().apply { addAll(this@bubbleSort) }
    val result = Result()
    val startTime = System.currentTimeMillis()
    var endTime = 0L

    sort@ for (i in 0 until list.lastIndex) {
        for (j in 0 until list.lastIndex) {
            if (list[j, true] > list[j + 1, true]) {
                list[j] = list[j + 1].apply { list[j + 1] = list[j] }
            }

            endTime = System.currentTimeMillis() - startTime
            if (endTime >= limitTime) {
                result.succeed = false
                break@sort
            }
        }
    }

    result.setTime(endTime)
    result.list = list
    return result
}

fun jumpSearch(source: MutableList<String>, find: MutableList<String>): Result {
    val result = Result()
    val startTime = System.currentTimeMillis()
    val blockSize = sqrt(source.size.toDouble()).toInt()

    find@ for (item in find) {
        for (si in 0 until source.size step blockSize) {
            if (item in source[si, true]) {
                result.entryNum++
                continue@find
            } else if (source[si, true] > item) {
                for (i in si - 1 downTo si - 2) {
                    if (item in source[i, true]) {
                        result.entryNum++
                        continue@find
                    }
                }
            }
        }

        if (source.lastIndex % blockSize != 0) {
            if (item in source.last()) {
                result.entryNum++
            } else if (source.last() > item) {
                for (i in source.lastIndex - 1 downTo source.lastIndex - 2) {
                    if (item in source[i, true]) {
                        result.entryNum++
                        break
                    }
                }
            }
        }
    }

    result.setTime(System.currentTimeMillis() - startTime)
    return result
}

fun MutableList<String>.quickSort(): Result {
    val list = mutableListOf<String>().apply { addAll(this@quickSort) }
    val result = Result()
    val startTime = System.currentTimeMillis()

    quickSort0(list, 0, list.lastIndex)

    result.setTime(System.currentTimeMillis() - startTime)
    result.list = list
    return result
}

fun quickSort0(list: MutableList<String>, left: Int, right: Int) {
    if (right <= left) return

    var pivot = left
    var cursor = right
    var swapBig = true

    do {
        if (swapBig) {
            if (list[pivot, true] > list[cursor, true]) {
                list[pivot] = list[cursor].apply { list[cursor] = list[pivot] }
                pivot = cursor.apply { cursor = pivot }
                swapBig = !swapBig
            }
        } else {
            if (list[pivot, true] < list[cursor, true]) {
                list[pivot] = list[cursor].apply { list[cursor] = list[pivot] }
                pivot = cursor.apply { cursor = pivot }
                swapBig = !swapBig
            }
        }

        if (swapBig) {
            cursor--
        } else {
            cursor++
        }
    } while (pivot != cursor)

    quickSort0(list, left, pivot - 1)
    quickSort0(list, pivot + 1, right)
}

fun binarySearch(source: MutableList<String>, find: MutableList<String>): Result {
    val result = Result()
    val startTime = System.currentTimeMillis()
    var entryNum = 0

    for (item in find) {
        var left = 0
        var right = source.lastIndex
        var middle = (left + right) / 2

        while (left <= right) {
            if (item in source[middle, true]) {
                entryNum++
                break
            }

            if (item < source[middle, true]) {
                right = middle - 1
                middle = (left + right) / 2
            } else if (item > source[middle, true]) {
                left = middle + 1
                middle = (left + right) / 2
            }
        }
    }

    result.setTime(System.currentTimeMillis() - startTime)
    result.entryNum = entryNum
    return result
}

fun printFoundResult(total: Int, result: Result) {
    println(
        "Found $total / ${result.entryNum} entries. Time taken: ${
            result.getTime(
                Calendar.MINUTE
            )
        } min. ${
            result.getTime(
                Calendar.SECOND
            )
        } sec. ${result.getTime(Calendar.MILLISECOND)}ms."
    )
}

fun printSortResult(result: Result) {
    println(
        "Sorting time: ${result.getTime(Calendar.MINUTE)} min. ${result.getTime(Calendar.SECOND)} sec. ${
            result.getTime(
                Calendar.MILLISECOND
            )
        }ms.${if (!result.succeed) " - STOPPED, moved to linear search" else ""}"
    )
}

fun printSearchResult(result: Result) {
    println(
        "Searching time: ${result.getTime(Calendar.MINUTE)} min. ${result.getTime(Calendar.SECOND)} sec. ${
            result.getTime(
                Calendar.MILLISECOND
            )
        }ms."
    )
}

operator fun List<String>.get(index: Int, spec: Boolean): String {
    return this[if (index < 0) 0 else index].substringAfter(' ')
}

class Result {
    private val takenTime: Calendar = Calendar.getInstance()

    var entryNum: Int = 0
    var succeed = true

    var list: MutableList<String>? = null

    fun timeInMillis(): Long {
        return takenTime.timeInMillis
    }

    /**
     * @param field [Calendar.MINUTE]、[Calendar.SECOND] etc.
     */
    fun getTime(field: Int): Int {
        return takenTime.get(field)
    }

    fun setTime(time: Long) {
        takenTime.timeInMillis = time
    }

    operator fun plus(other: Result): Result {
        val result = Result()

        result.setTime(timeInMillis() + other.timeInMillis())
        result.entryNum = entryNum + other.entryNum

        return result
    }

    override fun toString(): String {
        return """
            entryNum: $entryNum
            succeed: $succeed
            time: ${timeInMillis()}
            list: ${list?.joinToString(", ")}
        """.trimIndent()
    }
}