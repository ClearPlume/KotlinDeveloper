package parking

var free = booleanArrayOf(false, true)

fun main() {
    val inputs = readLine()!!.split(" ")
    val command = inputs[0]

    if (command == "park") {
        free[1] = false
        println("${inputs[2]} car parked in spot 2.")
    } else {
        val leaveSpotNum = inputs[1].toInt()

        if (free[leaveSpotNum - 1]) {
            println("There is no car in spot $leaveSpotNum.")
        } else {
            free[1] = true
            println("Spot $leaveSpotNum is free.")
        }
    }
}
