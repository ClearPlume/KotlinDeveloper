import java.lang.Integer.min

data class City(val name: String, val defaultDegrees: Int) {
    var degrees: Int = defaultDegrees
        set(value) {
            field = if (value < -92 || value > 57) defaultDegrees else value
        }
}

fun main() {
    val first = readLine()!!.toInt()
    val second = readLine()!!.toInt()
    val third = readLine()!!.toInt()
    val firstCity = City("Dubai", 30)
    firstCity.degrees = first
    val secondCity = City("Moscow", 5)
    secondCity.degrees = second
    val thirdCity = City("Hanoi", 20)
    thirdCity.degrees = third

    val minDegrees = min(min(firstCity.degrees, secondCity.degrees), thirdCity.degrees)
    val cities = arrayOf(firstCity, secondCity, thirdCity)
    var minDegreesNum = 0

    for (c in cities) {
        if (c.degrees == minDegrees) {
            minDegreesNum++
        }
    }

    print(if (minDegreesNum == 1) {
        var minCity: City? = null

        for (c in cities) {
            if (c.degrees == minDegrees) {
                minCity = c
                break
            }
        }
        minCity!!.name
    } else {
        "neither"
    })
}