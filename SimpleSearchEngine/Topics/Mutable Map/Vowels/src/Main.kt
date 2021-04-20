fun main() {
    val vowels = mutableMapOf('a' to 1, 'e' to 5, 'i' to 9, 'o' to 15, 'u' to 21)

    val letter = readLine()!!.first().toLowerCase()
    // write your code here
    println(vowels[letter] ?: 0)
}
