fun main() {
    val input = readLine()!!

    println(Rainbow.contains(input))
}

enum class Rainbow {
    RED, ORANGE, YELLOW, GREEN, BLUE, INDIGO, VIOLET;

    companion object {
        fun contains(name: String): Boolean {
            var contain = false

            for (value in values()) {
                if (value.name.equals(name, true)) {
                    contain = true
                    break
                }
            }

            return contain
        }
    }
}