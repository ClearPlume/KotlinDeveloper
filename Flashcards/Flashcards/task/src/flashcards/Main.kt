package flashcards

import java.util.*

val scanner = Scanner(System.`in`)
val cards = mutableMapOf<String, String>()

fun main() {
    println("Input the number of cards:")
    val cardNum = scanner.nextLine().toInt()

    for (num in 1..cardNum) {
        println("Card #$num:")
        val term = getInput(InputType.TERM)

        println("The definition for card #$num:")
        val definition = getInput(InputType.DEFINITION)

        cards[term] = definition
    }

    for (card in cards) {
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
}

fun getInput(type: InputType): String {
    var input: String

    when (type) {
        InputType.TERM -> {
            while (true) {
                input = scanner.nextLine()

                if (input in cards) {
                    println("The term \"$input\" already exists. Try again:")
                } else {
                    break
                }
            }
        }
        InputType.DEFINITION -> {
            while (true) {
                input = scanner.nextLine()

                if (input in cards.values) {
                    println("The definition \"$input\" already exists. Try again:")
                } else {
                    break
                }
            }
        }
    }

    return input
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

enum class InputType { TERM, DEFINITION }
