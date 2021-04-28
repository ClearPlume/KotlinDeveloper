package phonebook

import java.io.File
import java.util.*
import kotlin.math.sqrt

fun main() {
    val directory = readFile("E:\\small_directory.txt")
    val find = readFile("E:\\find.txt")

    val allFindEntryNum = find.size

    println("Start searching (linear search)...")

    val linearSearchResult = linearSearch(directory, find)

    println(
        "Found $allFindEntryNum / ${linearSearchResult.entryNum} entries. Time taken: ${
            linearSearchResult.getTime(
                Calendar.MINUTE
            )
        } min. ${
            linearSearchResult.getTime(
                Calendar.SECOND
            )
        } sec. ${linearSearchResult.getTime(Calendar.MILLISECOND)}ms."
    )

    println()
    println("Start searching (bubble sort + jump search)...")

    val sortResult = bubble(directory, linearSearchResult.timeInMillis() * 10L)
    val searchResult = if (sortResult.succeed) {
        jumpSearch(directory, find)
    } else {
        linearSearch(directory, find)
    }
    val foundResult = sortResult + searchResult

    println(
        "Found $allFindEntryNum / ${foundResult.entryNum} entries. Time taken: ${
            foundResult.getTime(
                Calendar.MINUTE
            )
        } min. ${
            foundResult.getTime(
                Calendar.SECOND
            )
        } sec. ${foundResult.getTime(Calendar.MILLISECOND)}ms."
    )
    println(
        "Sorting time: ${sortResult.getTime(Calendar.MINUTE)} min. ${sortResult.getTime(Calendar.SECOND)} sec. ${
            sortResult.getTime(
                Calendar.MILLISECOND
            )
        }ms."
    )
    println(
        "Searching time: ${searchResult.getTime(Calendar.MINUTE)} min. ${searchResult.getTime(Calendar.SECOND)} sec. ${
            searchResult.getTime(
                Calendar.MILLISECOND
            )
        }ms."
    )
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

fun bubble(list: MutableList<String>, limitTime: Long): Result {
    val result = Result()
    val startTime = System.currentTimeMillis()
    var endTime = 0L

    sort@ for (i in 0 until list.lastIndex) {
        for (j in 0 until list.lastIndex) {
            if (list[j] > list[j + 1]) {
                val tmp = list[j]
                list[j] = list[j + 1]
                list[j + 1] = tmp
            }

            endTime = System.currentTimeMillis() - startTime
            if (endTime >= limitTime) {
                result.succeed = false
                break@sort
            }
        }
    }

    result.setTime(endTime)
    return result
}

fun jumpSearch(source: MutableList<String>, find: MutableList<String>): Result {
    val result = Result()
    val startTime = System.currentTimeMillis()
    val blockSize = sqrt(source.size.toDouble()).toInt()

    for (item in find) {
        for (si in 0 until source.size step blockSize) {
            if (item in source[si]) {
                result.entryNum++
            }
        }

        if (source.lastIndex % blockSize == 0) {
            if (item in source.last()) {
                result.entryNum++
            }
        }
    }

    for (item in find) {
        if (source.find { item in it } != null) {
            result.entryNum++
        }
    }

    result.setTime(System.currentTimeMillis() - startTime)
    return result
}

class Result {
    private val takenTime: Calendar = Calendar.getInstance()

    var entryNum: Int = 0
    var succeed = true

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

        return result
    }
}