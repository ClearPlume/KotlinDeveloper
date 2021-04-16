import java.util.*

data class Client(val name: String, val age: Int, val balance: Int) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Client

        if (name != other.name) return false
        if (age != other.age) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + age
        return result
    }
}

fun main() {
    // implement your code here
    val scanner = Scanner(System.`in`)
    val (client1, client2) = Array(2) { Client(scanner.next(), scanner.nextInt(), scanner.nextInt()) }
    println(client1 == client2)
}