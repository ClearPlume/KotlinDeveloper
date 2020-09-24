package signature

import java.util.*
import kotlin.math.max

fun main() {
    val scanner = Scanner(System.`in`)

    val name = getNameStr(scanner.nextLine().toUpperCase())
    val status = scanner.nextLine()
    val nameLength = name[0].length
    val statusLength = status.length
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

    // 6 is the "*  " at the beginning and "  *" at the end of the line
    println("*".repeat(max(nameLength, statusLength) + 6))
    if (nameLength > statusLength) {
        printName(name, nameSpace)
        printStatus(status, statusSpace, exceedLength)
    } else {
        printName(name, nameSpace, exceedLength)
        printStatus(status, statusSpace)
    }
    print("*".repeat(max(nameLength, statusLength) + 6))
}

fun printStatus(status: String, spaces: Int, exceedLength: Int = 0) {
    print("*  ")
    print(" ".repeat(spaces / 2))
    print(status)
    print(" ".repeat(spaces / 2 + exceedLength))
    println("  *")
}

fun printName(name: Array<String>, spaces: Int, exceedLength: Int = 0) {
    for (s in name) {
        print("*  ")
        print(" ".repeat(spaces / 2))
        print(s)
        print(" ".repeat(spaces / 2 + exceedLength))
        println("  *")
    }
}

fun getNameStr(name: String): Array<String> {
    return arrayOf(
            getNameSingleLine(name, 0),
            getNameSingleLine(name, 1),
            getNameSingleLine(name, 2)
    )
}

fun getNameSingleLine(name: String, lineNum: Int): String {
    var tmp = ""

    for (i in name.indices) {
        val charIndex: Int = if (name[i] == ' ') 26 else (name[i] - 65).toInt()
        tmp += letters[charIndex][lineNum]

        if (i < name.lastIndex) {
            tmp += ' '
        }
    }
    return tmp
}

val letters = arrayOf(
        arrayOf(
                "____",
                "|__|",
                "|  |"
        ),
        arrayOf(
                "___ ",
                "|__]",
                "|__]"
        ),
        arrayOf(
                "____",
                "|   ",
                "|___"
        ),
        arrayOf(
                "___ ",
                "|  \\",
                "|__/"
        ),
        arrayOf(
                "____",
                "|___",
                "|___",
        ),
        arrayOf(
                "____",
                "|___",
                "|   "
        ),
        arrayOf(
                "____",
                "| __",
                "|__]"
        ),
        arrayOf(
                "_  _",
                "|__|",
                "|  |"
        ),
        arrayOf(
                "_",
                "|",
                "|"
        ),
        arrayOf(
                " _",
                " |",
                "_|"
        ),
        arrayOf(
                "_  _",
                "|_/ ",
                "| \\_"
        ),
        arrayOf(
                "_   ",
                "|   ",
                "|___"
        ),
        arrayOf(
                "_  _",
                "|\\/|",
                "|  |"
        ),
        arrayOf(
                "_  _",
                "|\\ |",
                "| \\|"
        ),
        arrayOf(
                "____",
                "|  |",
                "|__|"
        ),
        arrayOf(
                "___ ",
                "|__]",
                "|   "
        ),
        arrayOf(
                "____",
                "|  |",
                "|_\\|"
        ),
        arrayOf(
                "____",
                "|__/",
                "|  \\"
        ),
        arrayOf(
                "____",
                "[__ ",
                "___]"
        ),
        arrayOf(
                "___",
                " | ",
                " | "
        ),
        arrayOf(
                "_  _",
                "|  |",
                "|__|"
        ),
        arrayOf(
                "_  _",
                "|  |",
                " \\/ "
        ),
        arrayOf(
                "_ _ _",
                "| | |",
                "|_|_|"
        ),
        arrayOf(
                "_  _",
                " \\/ ",
                "_/\\_"
        ),
        arrayOf(
                "_   _",
                " \\_/ ",
                "  |  "
        ),
        arrayOf(
                "___ ",
                "  / ",
                " /__"
        ),
        arrayOf(
                "    ",
                "    ",
                "    "
        )
)