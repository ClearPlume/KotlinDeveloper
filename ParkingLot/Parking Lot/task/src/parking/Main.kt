package parking

var state: MutableMap<Int, Car?>? = null

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
                    state!![num] = null
                }

                println("Created a parking lot with $spotsNumber spots.")
            }
            "park" -> {
                if (isParkingLotCreated()) {
                    if (state!!.none { it.value == null }) {
                        println("Sorry, the parking lot is full.")
                        continue
                    }
                    val carNumber = inputs[1]
                    val carColor = inputs[2]

                    val carStopPosition = state!!.entries.first { it.value == null }.key
                    state!![carStopPosition] = Car(carNumber, carColor)

                    println("$carColor car parked in spot $carStopPosition.")
                } else {
                    parkingLotNotInitialized()
                }
            }
            "leave" -> {
                if (isParkingLotCreated()) {
                    val leaveSpotNum = inputs[1].toInt()

                    if (state!![leaveSpotNum] == null) {
                        println("There is no car in spot $leaveSpotNum.")
                    } else {
                        state!![leaveSpotNum] = null
                        println("Spot $leaveSpotNum is free.")
                    }
                } else {
                    parkingLotNotInitialized()
                }
            }
            "status" -> {
                if (isParkingLotCreated()) {
                    val parkedCars = findCars { it != null }

                    if (parkedCars.isEmpty()) {
                        println("Parking lot is empty.")
                    } else {
                        for (parking in parkedCars) {
                            println("${parking.key} ${parking.value}")
                        }
                    }
                } else {
                    parkingLotNotInitialized()
                }
            }
            "reg_by_color" -> {
                if (isParkingLotCreated()) {
                    val color = inputs[1].toLowerCase()
                    val coloredCars = findCars { it?.color?.toLowerCase() == color }

                    if (coloredCars.isEmpty()) {
                        println("No cars with color $color were found.")
                    } else {
                        println(coloredCars.values.map { it?.reg }.joinToString(", "))
                    }
                } else {
                    parkingLotNotInitialized()
                }
            }
            "spot_by_color" -> {
                if (isParkingLotCreated()) {
                    val color = inputs[1].toLowerCase()
                    val coloredCars = findCars { it?.color?.toLowerCase() == color }

                    if (coloredCars.isEmpty()) {
                        println("No cars with color $color were found.")
                    } else {
                        println(coloredCars.keys.joinToString(", "))
                    }
                } else {
                    parkingLotNotInitialized()
                }
            }
            "spot_by_reg" -> {
                if (isParkingLotCreated()) {
                    val reg = inputs[1]
                    val carByReg = findCars { it?.reg == reg }

                    if (carByReg.isEmpty()) {
                        println("No cars with registration number $reg were found.")
                    } else {
                        println(carByReg.keys.first())
                    }
                } else {
                    parkingLotNotInitialized()
                }
            }
        }
    }
}

fun findCars(predicate: (Car?) -> Boolean) = state!!.filterValues(predicate)

fun isParkingLotCreated() = state != null

fun parkingLotNotInitialized() = println("Sorry, a parking lot has not been created.")

data class Car(val reg: String, val color: String) {
    override fun toString(): String {
        return "$reg $color"
    }
}