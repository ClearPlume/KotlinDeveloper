package phonebook

import java.io.File
import java.util.*

fun main() {
    val fileDirectory = File("E:\\directory.txt")
    val directory = mutableListOf<String>()
    fileDirectory.forEachLine { directory.add(it) }

    val fileFind = File("E:\\find.txt")
    val find = mutableListOf<String>()
    fileFind.forEachLine { find.add(it) }
    val allFindEntryNum = find.size
    var foundEntryNum = 0

    val timeMillisBeforeSearch = System.currentTimeMillis()
    println("Start searching...")

    for (item in find) {
        if (directory.find { item in it } != null) {
            foundEntryNum++
        }
    }

    val timeTaken = Calendar.getInstance()
    timeTaken.timeInMillis = System.currentTimeMillis() - timeMillisBeforeSearch

    println(
        "Found $allFindEntryNum / $foundEntryNum entries. Time taken: ${timeTaken.get(Calendar.MINUTE)} min. ${
            timeTaken.get(
                Calendar.SECOND
            )
        } sec. ${timeTaken.get(Calendar.MILLISECOND)}ms."
    )
}
