import java.util.*

// write your function here
fun isVowel(letter: Char) = "aeiou".contains(letter, true)

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val letter = scanner.next()[0]

    println(isVowel(letter))
}