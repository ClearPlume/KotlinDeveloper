fun main() {
    // write your code here
    val username = readLine()!!

    if ("HIDDEN" == username) {
        greeting()
    } else {
        greeting(username)
    }
}

fun greeting(username: String = "secret user") = println("Hello, $username!")