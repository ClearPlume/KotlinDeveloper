package machine

import java.util.*

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
    state()

    print("Write action (buy, fill, take):")
    when (scanner.next()) {
        "buy" -> buy()
        "fill" -> fill()
        "take" -> take()
    }

    state()
}

fun state() {
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
    print("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino:")
    when (scanner.nextInt()) {
        1 -> updateState(espresso)
        2 -> updateState(latte)
        3 -> updateState(cappuccino)
    }
}

fun updateState(coffee: Coffee) {
    water -= coffee.water
    milk -= coffee.milk
    beans -= coffee.beans
    money += coffee.money
    cups--
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
