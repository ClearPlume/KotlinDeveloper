fun main() {
    val strs = Array(3) { readLine()!! }
    val separator = readLine()!!
    
    println(if ("NO SEPARATOR" == separator) join(strs) else join(strs, separator))
}

fun join(strs: Array<String>, separator: String = " ") = strs.joinToString(separator)
