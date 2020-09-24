fun main() {
    val input = java.util.Scanner(System.`in`)

    val word = input.next()

    for (c in word) {
        if (c.isDigit()) {
            print(c)
            break
        }
    }
}