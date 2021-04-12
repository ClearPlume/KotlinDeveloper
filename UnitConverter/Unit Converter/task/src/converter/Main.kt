package converter

fun main() {
    print("Enter a number of kilometers: ")
    val kilometers = readLine()!!.toInt()

    println("$kilometers kilometers is ${kilometers * 1000} meters")
}
