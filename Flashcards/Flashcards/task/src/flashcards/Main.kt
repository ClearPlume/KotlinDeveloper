package flashcards

import java.io.File
import java.io.FileOutputStream
import java.io.PrintStream
import java.util.*

val scanner = Scanner(System.`in`)

val cards = mutableListOf<Card>()
val logs = mutableListOf<String>()

fun main() {
    while (true) {
        when (getAction()) {
            "add" -> {
                println("The card:")
                val term = nextLine(scanner)

                if (term in cards.map { it.term }) {
                    println("The card \"$term\" already exists.")
                    continue
                }

                println("The definition of the card:")
                val definition = nextLine(scanner)

                if (definition in cards.map { it.definition }) {
                    println("The definition \"$definition\" already exists.")
                    continue
                }

                cards[term, definition] = 0
                println("The pair (\"$term\":\"$definition\") has been added.")
                println()
            }
            "remove" -> {
                println("Which card?")
                val term = nextLine(scanner)

                if (term in cards.map { it.term }) {
                    cards.remove(term)
                    println("The card has been removed.")
                } else {
                    println("Can't remove \"$term\": there is no such card.")
                }
                println()
            }
            "import" -> {
                println("File name:")
                val fileName = nextLine(scanner)
                val file = File(fileName)

                if (file.exists()) {
                    var importedNum = 0

                    file.forEachLine {
                        val (term, definition, mistakes) = it.split(Regex("[=:]"))
                        cards[term, definition] = mistakes.toInt()
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
                val file = nextLine(scanner)
                val printer = PrintStream(FileOutputStream(file))

                for (card in cards) {
                    printer.println("${card.term}=${card.definition}:${card.mistakes}")
                }

                printer.flush()
                printer.close()
                println("${cards.size} cards have been saved.")

                println()
            }
            "ask" -> {
                println("How many times to ask?")
                val times = nextLine(scanner).toInt()

                repeat(times) {
                    val card = cards.random()
                    println("Print the definition of \"${card.term}\":")
                    val userInput = nextLine(scanner)

                    if (userInput == card.definition) {
                        println("Correct!")
                    } else {
                        card.mistakes++
                        val answer = cards[card.term]?.definition
                        val rightAnother = userInput in cards.map { it.definition }
                        val another = cards.findTermByDefinition(userInput)

                        println("Wrong. The right answer is \"$answer\"${if (rightAnother) ", but your definition is correct for \"$another\"." else "."}")
                    }
                }

                println()
            }
            "exit" -> {
                println("Bye bye!")
                break
            }
            "log" -> {
                println("File name:")
                val file = nextLine(scanner)
                val printer = PrintStream(FileOutputStream(file))

                for (log in logs) {
                    printer.println(log)
                }

                printer.flush()
                printer.close()
                println("The log has been saved.")

                println()
            }
            "hardest card" -> {
                val errorTimes = cards.maxOfOrNull { it.mistakes }

                if (errorTimes != null && errorTimes != 0) {
                    val hardestCard = cards.filter { it.mistakes == errorTimes }
                    val oneHardest = hardestCard.size == 1
                    val hardestCardStr = hardestCard.joinToString(", ", " ") { "\"" + it.term + "\"" }

                    println("The hardest ${if (oneHardest) "card" else "cards"} ${if (oneHardest) "is" else "are"}$hardestCardStr. You have $errorTimes errors answering ${if (oneHardest) "it" else "them"}.")
                } else {
                    println("There are no cards with errors.")
                }

                println()
            }
            "reset stats" -> {
                for (card in cards) {
                    card.mistakes = 0
                }
                println("Card statistics have been reset.")

                println()
            }
            else -> {
                println("Wrong command, try again.")
                println()
            }
        }
    }
}

fun getAction(): String {
    println("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):")
    return nextLine(scanner)
}

fun println() {
    logs.add(System.lineSeparator())
    kotlin.io.println()
}

fun println(msg: String) {
    logs.add(msg)
    kotlin.io.println(msg)
}

fun nextLine(scanner: Scanner): String {
    val nextLine = scanner.nextLine()
    logs.add(nextLine)
    return nextLine
}

fun MutableList<Card>.remove(term: String) {
    get(term)?.let { remove(it) }
}

fun MutableList<Card>.findTermByDefinition(definition: String): String? {
    var term: String? = null

    for (card in this) {
        if (card.definition == definition) {
            term = card.term
            break
        }
    }

    return term
}

operator fun MutableList<Card>.get(term: String): Card? {
    var card: Card? = null

    for (c in this) {
        if (c.term == term) {
            card = c
            break
        }
    }

    return card
}

/**
 * Only for the present situation, it is only added, not set.
 */
operator fun MutableList<Card>.set(term: String, definition: String, mistakes: Int) {
    remove(term)
    add(Card(term, definition, mistakes))
}

data class Card(val term: String, val definition: String, var mistakes: Int = 0) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Card

        if (term != other.term) return false

        return true
    }

    override fun hashCode(): Int {
        return term.hashCode()
    }
}
