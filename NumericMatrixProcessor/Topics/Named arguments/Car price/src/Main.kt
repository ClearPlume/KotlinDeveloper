import java.util.*

fun main(args: Array<String>) {
    // write your code here
    val scanner = Scanner(System.`in`)

    when (scanner.next()) {
        "old" -> {
            println(calcPrice(old = scanner.nextInt()))
        }
        "passed" -> {
            println(calcPrice(passed = scanner.nextInt()))
        }
        "speed" -> {
            println(calcPrice(speed = scanner.nextInt()))
        }
        "auto" -> {
            println(calcPrice(auto = scanner.nextInt()))
        }
        else -> {
            println(calcPrice())
        }
    }
}

fun calcPrice(old: Int = 5, passed: Int = 100_000, speed: Int = 120, auto: Int = 0): Int {
    var initialPrice = 20_000

    initialPrice -= old * 2000

    initialPrice -= passed / 10_000 * 200

    if (speed > 120) {
        initialPrice += (speed - 120) * 100
    } else {
        initialPrice -= (120 - speed) * 100
    }

    initialPrice += auto * 1500

    return initialPrice
}