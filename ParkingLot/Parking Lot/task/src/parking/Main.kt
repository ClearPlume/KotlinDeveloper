package parking

var state = Array(20) { false }

fun main() {
    while (true) {
        val input = readLine()!!
        if (input == "exit") break
        val inputs = input.split(' ')
        val command = inputs[0]

        if (command == "park") {
            if (state.none { !it }) {
                println("Sorry, the parking lot is full.")
                continue
            }
            val carStopIndex = state.indexOfFirst { !it }
            state[carStopIndex] = true
            println("${inputs[2]} car parked in spot ${carStopIndex + 1}.")
        } else {
            val leaveSpotNum = inputs[1].toInt()
            val leaveSpotIndex = leaveSpotNum - 1

            if (!state[leaveSpotIndex]) {
                println("There is no car in spot $leaveSpotNum.")
            } else {
                state[leaveSpotIndex] = false
                println("Spot $leaveSpotNum is free.")
            }
        }
    }
}