package flashcards

import java.io.File
import java.io.FileOutputStream
import java.io.PrintStream
import java.util.*

val scanner = Scanner(System.`in`)
val cards = mutableMapOf<String, String>()

fun main() {
    while (true) {
        when (getAction()) {
            "add" -> {
                println("The card:")
                val term = scanner.nextLine()

                if (term in cards) {
                    println("The card \"$term\" already exists.")
                    continue
                }

                println("The definition of the card:")
                val definition = scanner.nextLine()

                if (definition in cards.values) {
                    println("The definition \"$definition\" already exists.")
                    continue
                }

                cards[term] = definition
                println("The pair (\"$term\":\"$definition\") has been added.")
                println()
            }
            "remove" -> {
                println("Which card?")
                val term = scanner.nextLine()

                if (term in cards) {
                    cards.remove(term)
                    println("The card has been removed.")
                } else {
                    println("Can't remove \"$term\": there is no such card.")
                }
                println()
            }
            "import" -> {
                println("File name:")
                val fileName = scanner.nextLine()
                val file = File(fileName)

                if (file.exists()) {
                    var importedNum = 0

                    file.forEachLine {
                        val (term, definition) = it.split("=")
                        cards[term] = definition
                        importedNum++
                    }

                    println("$importedNum cards have been loaded.")
                } else {
                    println("File not found.")
                }

                println()
            }
            "export" -> {
                println("File name:")
                val file = scanner.nextLine()
                val printer = PrintStream(FileOutputStream(file))

                for (card in cards) {
                    printer.println("${card.key}=${card.value}")
                }

                printer.flush()
                printer.close()
                println("${cards.size} cards have been saved.")

                println()
            }
            "ask" -> {
                println("How many times to ask?")
                val times = scanner.nextLine().toInt()

                repeat(times) {
                    val card = cards.random()
                    println("Print the definition of \"${card.key}\":")
                    val userInput = scanner.nextLine()

                    if (userInput == card.value) {
                        println("Correct!")
                    } else {
                        val answer = cards[card.key]
                        val another = cards.findKbyV(userInput)
                        val rightAnother = userInput in cards.values

                        println("Wrong. The right answer is \"$answer\"${if (rightAnother) ", but your definition is correct for \"$another\"." else "."}")
                    }
                }

                println()
            }
            "exit" -> {
                println("Bye bye!")
                break
            }
            else -> {
                println("Wrong command, try again.")
                println()
            }
        }
    }
}

fun getAction(): String {
    println("Input the action (add, remove, import, export, ask, exit):")
    return scanner.nextLine()
}

fun <K, V> MutableMap<K, V>.random(): MutableMap.MutableEntry<K, V> {
    return this.entries.random()
}

fun <K, V> MutableMap<K, V>.findKbyV(value: V): K? {
    var k: K? = null

    for (entry in this) {
        if (entry.value == value) {
            k = entry.key
            break
        }
    }

    return k
}
