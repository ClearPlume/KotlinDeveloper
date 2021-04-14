package machine

import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)

    println("Write how many ml of water the coffee machine has:")
    val water = scanner.nextInt()

    println("Write how many ml of milk the coffee machine has:")
    val milk = scanner.nextInt()

    println("Write how many grams of coffee beans the coffee machine has:")
    val gram = scanner.nextInt()

    println("Write how many cups of coffee you will need:")
    val tar = scanner.nextInt()

    val cups = arrayOf(water / 200, milk / 50, gram / 15).minOf { it }

    println(
        when {
            cups < tar -> "No, I can make only $cups cups of coffee"
            cups > tar -> "Yes, I can make that amount of coffee (and even ${cups - tar} more than that)"
            else -> "Yes, I can make that amount of coffee"
        }
    )
}
