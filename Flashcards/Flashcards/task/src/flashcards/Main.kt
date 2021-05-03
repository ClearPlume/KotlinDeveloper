package flashcards

import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)

    println("Input the number of cards:")
    val cardNum = scanner.nextLine().toInt()
    val cards = mutableMapOf<String, String>()

    for (num in 1..cardNum) {
        println("Card #$num:")
        val term = scanner.nextLine()

        println("The definition for card #$num:")
        val definition = scanner.nextLine()

        cards[term] = definition
    }

    for (card in cards) {
        println("Print the definition of \"${card.key}\":")
        val userInput = scanner.nextLine()

        println(if (userInput == card.value) "Correct!" else "Wrong. The right answer is \"${card.value}\".")
    }
}
