package converter

import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)

    print("Enter a number and a measure of length: ")
    val number = scanner.nextDouble()
    var measure = scanner.next().toLowerCase()
    var result = -1.0

    when (measure) {
        "m", "meter", "meters" -> {
            result = number * 1
            measure = if (number != 1.0) "meters" else "meter"
        }
        "km", "kilometer", "kilometers" -> {
            result = number * 1000
            measure = if (number != 1.0) "kilometers" else "kilometer"
        }
        "cm", "centimeter", "centimeters" -> {
            result = number / 100
            measure = if (number != 1.0) "centimeters" else "centimeter"
        }
        "mm", "millimeter", "millimeters" -> {
            result = number / 1000
            measure = if (number != 1.0) "millimeters" else "millimeter"
        }
        "mi", "mile", "miles" -> {
            result = number * 1609.35
            measure = if (number != 1.0) "miles" else "mile"
        }
        "yd", "yard", "yards" -> {
            result = number * 0.9144
            measure = if (number != 1.0) "yards" else "yard"
        }
        "ft", "foot", "feet" -> {
            result = number * 0.3048
            measure = if (number != 1.0) "feet" else "foot"
        }
        "in", "inch", "inches" -> {
            result = number * 0.0254
            measure = if (number != 1.0) "inches" else "inch"
        }
    }

    println("\n$number $measure is $result ${if (result != 1.0) "meters" else "meter"}")
}
