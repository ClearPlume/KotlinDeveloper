package parking

var state: MutableMap<Int, String>? = null

fun main() {
    while (true) {
        val input = readLine()!!
        if (input == "exit") break
        val inputs = input.split(' ')

        when (inputs[0]) {
            "create" -> {
                val spotsNumber = inputs[1].toInt()

                if (isParkingLotCreated()) {
                    state!!.clear()
                } else {
                    state = HashMap()
                }

                for (num in 1..spotsNumber) {
                    state!![num] = ""
                }

                println("Created a parking lot with $spotsNumber spots.")
            }
            "park" -> {
                if (isParkingLotCreated()) {
                    if (state!!.none { it.value.isEmpty() }) {
                        println("Sorry, the parking lot is full.")
                        continue
                    }
                    val carNumber = inputs[1]
                    val carColor = inputs[2]
                    val carStopPosition = state!!.entries.first { it.value.isEmpty() }.key
                    state!![carStopPosition] = "$carNumber $carColor"
                    println("$carColor car parked in spot $carStopPosition.")
                } else {
                    println("Sorry, a parking lot has not been created.")
                }
            }
            "leave" -> {
                if (isParkingLotCreated()) {
                    val leaveSpotNum = inputs[1].toInt()

                    if (state!![leaveSpotNum]?.isEmpty() == true) {
                        println("There is no car in spot $leaveSpotNum.")
                    } else {
                        state!![leaveSpotNum] = ""
                        println("Spot $leaveSpotNum is free.")
                    }
                } else {
                    println("Sorry, a parking lot has not been created.")
                }
            }
            "status" -> {
                if (isParkingLotCreated()) {
                    if (state!!.filterValues { it.isNotEmpty() }.isEmpty()) {
                        println("Parking lot is empty.")
                    } else {
                        for (parking in state!!.filterValues { it.isNotEmpty() }) {
                            println("${parking.key} ${parking.value}")
                        }
                    }
                } else {
                    println("Sorry, a parking lot has not been created.")
                }
            }
        }
    }
}

fun isParkingLotCreated() = state != null