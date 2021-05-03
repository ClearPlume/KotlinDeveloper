package flashcards

fun main() {
    val (_, definition, answer) = Array(3) { readLine()!! }
    println(if (definition == answer) "Your answer is right!" else "Your answer is wrong...")
}
