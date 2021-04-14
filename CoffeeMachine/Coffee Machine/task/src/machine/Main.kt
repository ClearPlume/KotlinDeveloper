package machine

import java.util.*
import kotlin.system.exitProcess

val scanner = Scanner(System.`in`)

val espresso = Coffee(250, beans = 16, money = 4)
val latte = Coffee(350, 75, 20, 7)
val cappuccino = Coffee(200, 100, 12, 6)

var water = 400
var milk = 540
var beans = 120
var cups = 9
var money = 550

fun main() {
    while (true) {
        print("Write action (buy, fill, take, remaining, exit):")
        when (scanner.next()) {
            "buy" -> buy()
            "fill" -> fill()
            "take" -> take()
            "remaining" -> remaining()
            "exit" -> exitProcess(0)
        }
    }
}

fun remaining() {
    println(
        """
        The coffee machine has:
        $water of water
        $milk of milk
        $beans of coffee beans
        $cups of disposable cups
        $money of money
    """.trimIndent()
    )
}

fun buy() {
    print("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:")
    val choose = scanner.next()

    if (choose != "back") {
        val result = when (choose.toInt()) {
            1 -> updateState(espresso)
            2 -> updateState(latte)
            3 -> updateState(cappuccino)
            else -> -99999
        }

        println(
            when (result) {
                -1 -> "Sorry, not enough water!"
                -2 -> "Sorry, not enough milk!"
                -3 -> "Sorry, not enough coffee beans!"
                -4 -> "Sorry, not enough cup!"
                else -> "I have enough resources, making you a coffee!"
            }
        )
    }
}

fun updateState(coffee: Coffee): Int {
    if (water < coffee.water) return -1
    if (milk < coffee.milk) return -2
    if (beans < coffee.beans) return -3
    if (cups < 1) return -4

    water -= coffee.water
    milk -= coffee.milk
    beans -= coffee.beans
    money += coffee.money
    cups--

    return 0
}

fun fill() {
    print("Write how many ml of water do you want to add:")
    water += scanner.nextInt()

    print("Write how many ml of milk do you want to add:")
    milk += scanner.nextInt()

    print("Write how many grams of coffee beans do you want to add:")
    beans += scanner.nextInt()

    print("Write how many disposable cups of coffee do you want to add:")
    cups += scanner.nextInt()
}

fun take() {
    println("I gave you $$money")
    money = 0
}

data class Coffee(var water: Int = 0, var milk: Int = 0, var beans: Int = 0, var money: Int = 0)
