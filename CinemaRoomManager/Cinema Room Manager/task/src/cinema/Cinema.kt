package cinema

import java.util.*

fun main() {
    // write your code here
    val scanner = Scanner(System.`in`)

    println("Enter the number of rows:")
    val rows = scanner.nextInt() + 1

    println("Enter the number of seats in each row:")
    val cols = scanner.nextInt() + 1

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

    do {
        showMenu("Show the seats", "Buy a ticket")
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
                println("Enter a row number:")
                val row = scanner.nextInt()

                println("Enter a seat number in that row:")
                val col = scanner.nextInt()

                seats[row][col] = "B "

                val seatsNum = (rows - 1) * (cols - 1)

                val ticketPrice = if (seatsNum <= 60) {
                    10
                } else {
                    if (row in 1..(rows - 1) / 2) 10 else 8
                }

                println("Ticket price: $$ticketPrice")
            }
            else -> break
        }
    } while (true)

    // val totalIncome = if (seatsNum <= 20) {
    //     seatsNum * 10
    // } else {
    //     if (rows % 2 == 0) {
    //         cols * (rows / 2) * 10 + cols * (rows / 2) * 8
    //     } else {
    //         cols * (rows / 2) * 10 + cols * (rows / 2 + 1) * 8
    //     }
    // }
    //
    // println("Total income:")
    // println("$$totalIncome")
}

fun showMenu(vararg items: String) {
    items.forEachIndexed { index, item -> println("${index + 1}. $item") }
}
