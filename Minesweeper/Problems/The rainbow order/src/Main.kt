fun main(args: Array<String>) {
    println(Rainbow.valueOf(readLine()!!.toUpperCase()).ordinal + 1)
}

enum class Rainbow {
    RED, ORANGE, YELLOW, GREEN, BLUE, INDIGO, VIOLET
}