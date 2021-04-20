fun main() {
    var word = readLine()!!
    val words = mutableMapOf<String, Int>()

    while (word != "stop") {
        words[word] = (words[word] ?: 0) + 1
        word = readLine()!!
    }

    print(words.entries.maxByOrNull { it.value }?.key)
}
