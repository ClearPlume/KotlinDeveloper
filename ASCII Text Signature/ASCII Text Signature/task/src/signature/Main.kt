package signature

import java.io.File
import java.util.*
import kotlin.math.max

fun main() {
    val scanner = Scanner(System.`in`)

    val name = getStr(scanner.nextLine(), "roman")
    val status = getStr(scanner.nextLine(), "medium")
    val nameLength = name[0].length
    val statusLength = status[0].length
    val nameSpace: Int
    val statusSpace: Int
    val exceedLength: Int

    if (nameLength > statusLength) {
        nameSpace = 0
        statusSpace = nameLength - statusLength
    } else {
        nameSpace = statusLength - nameLength
        statusSpace = 0
    }
    exceedLength = if (nameLength % 2 == 0 && statusLength % 2 == 1 || nameLength % 2 == 1 && statusLength % 2 == 0) {
        1
    } else {
        0
    }

    // 8 is the "88  " at the beginning and "  88" at the end of the line.
    println("8".repeat(max(nameLength, statusLength) + 8))
    if (nameLength > statusLength) {
        printStr(name, nameSpace)
        printStr(status, statusSpace, exceedLength)
    } else {
        printStr(name, nameSpace, exceedLength)
        printStr(status, statusSpace)
    }
    print("8".repeat(max(nameLength, statusLength) + 8))
}

fun printStr(name: Array<String>, spaces: Int, exceedLength: Int = 0) {
    for (s in name) {
        print("88  ")
        print(" ".repeat(spaces / 2))
        print(s)
        print(" ".repeat(spaces / 2 + exceedLength))
        println("  88")
    }
}

fun getStr(str: String, font: String): Array<String> {
    val array = Array(if (font == "medium") 3 else 10) { "" }
    for (i in array.indices) {
        array[i] = getStrSingleLine(font, str, i)
    }
    return array
}

fun getStrSingleLine(font: String, str: String, lineNum: Int): String {
    var tmp = ""

    for (i in str.indices) {
        tmp += if (str[i] == ' ') {
            if (font == "medium") "     " else "          "
        } else {
            val charIndex = if (str[i] in 'a'..'z') (str[i] - 97).toInt() else (str[i] - 65 + 26).toInt()
            (if (font == "medium") mediums else romans)!![charIndex][lineNum]
        }
    }
    return tmp
}

var romansInitialized = false
var romans: Array<Array<String>>? = null
    get() {
        return if (romansInitialized) {
            field
        } else {
            fill("roman")
        }
    }

var mediumsInitialized = false
var mediums: Array<Array<String>>? = null
    get() {
        return if (mediumsInitialized) {
            field
        } else {
            fill("medium")
        }
    }

fun fill(fontName: String): Array<Array<String>> {
    val scanner = Scanner(File("C:/fonts/$fontName.txt"))
    val fontInfo = scanner.nextLine().split(' ')
    val fontHeight = fontInfo[0].toInt()
    val letterNum = fontInfo[1].toInt()
    val fonts = Array(letterNum) { Array(fontHeight) { "" } }

    for (i in 0 until letterNum) {
        val letterWidth = scanner.nextLine().split(' ')[1].toInt()

        for (j in 0 until fontHeight) {
            fonts[i][j] = scanner.nextLine().substring(0, letterWidth)
        }
    }

    return fonts
}