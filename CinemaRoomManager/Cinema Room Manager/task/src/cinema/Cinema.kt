package cinema

import java.math.RoundingMode
import java.util.*

fun main() {
    // write your code here
    val scanner = Scanner(System.`in`)

    println("Enter the number of rows:")
    var rows = scanner.nextInt() + 1

    println("Enter the number of seats in each row:")
    var cols = scanner.nextInt() + 1

    val seats = Array(rows) { col ->
        if (col == 0) {
            Array(cols) { row ->
                if (row == 0) {
                    "  "
                } else {
                    "$row${if (row < cols) ' ' else '\u0000'}"
                }
            }
        } else {
            Array(cols) { row ->
                if (row == 0) {
                    "$col "
                } else {
                    "S${if (row < cols) ' ' else '\u0000'}"
                }
            }
        }
    }
    rows--
    cols--
    val seatsNum = rows * cols
    var purchasedNum = 0
    var currentIncome = 0

    do {
        showMenu("Show the seats", "Buy a ticket", "Statistics")
        println("0. Exit")

        when (scanner.nextInt()) {
            1 -> {
                println("Cinema:")
                seats.forEach {
                    it.forEach { seat -> print(seat) }
                    println()
                }
            }
            2 -> {
                var row = -1
                var col = -1

                do {
                    do {
                        println("Enter a row number:")
                        try {
                            row = scanner.nextInt()

                            if (row < 0 || row > rows) {
                                row = -1
                                throw NumberFormatException()
                            }
                        } catch (e: NumberFormatException) {
                            println("Wrong input!")
                        }
                    } while (row == -1)

                    do {
                        println("Enter a seat number in that row:")
                        try {
                            col = scanner.nextInt()

                            if (col < 0 || col > cols) {
                                col = -1
                                throw NumberFormatException()
                            }
                        } catch (e: NumberFormatException) {
                            println("Wrong input!")
                        }
                    } while (col == -1)

                    if (seats[row][col] == "B ") {
                        println("That ticket has already been purchased!")
                    }
                } while (seats[row][col] == "B ")

                seats[row][col] = "B "
                purchasedNum++

                val ticketPrice = if (seatsNum <= 60) {
                    10
                } else {
                    if (row in 1..rows / 2) 10 else 8
                }
                currentIncome += ticketPrice

                println("Ticket price: $$ticketPrice")
            }
            3 -> {
                println("Number of purchased tickets: $purchasedNum")
                println(
                    "Percentage: ${
                        (purchasedNum / seatsNum.toDouble() * 100).toBigDecimal().setScale(2, RoundingMode.HALF_UP)
                    }%"
                )
                println("Current income: $$currentIncome")

                val totalIncome = if (seatsNum <= 60) {
                    seatsNum * 10
                } else {
                    if (rows % 2 == 0) {
                        cols * (rows / 2) * 10 + cols * (rows / 2) * 8
                    } else {
                        cols * (rows / 2) * 10 + cols * (rows / 2 + 1) * 8
                    }
                }

                println("Total income: $$totalIncome")
            }
            0 -> break
            else -> println("Wrong input!")
        }
    } while (true)
}

fun showMenu(vararg items: String) {
    items.forEachIndexed { index, item -> println("${index + 1}. $item") }
}
