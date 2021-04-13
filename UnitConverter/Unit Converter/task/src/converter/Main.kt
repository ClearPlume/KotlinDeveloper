package converter

const val IMPOSSIBLE = -1.0

fun main() {
    while (true) {
        print("Enter what you want to convert (or exit): ")
        val input = readLine()!!.toLowerCase()
        var result: Double

        if (input != "exit") {
            val (_num, from, _, to) = input.split(" ")
            val num = _num.toDouble()
            val src = Unit.matchType(from)
            val tar = Unit.matchType(to)

            result = src[num, tar]

            if (result != IMPOSSIBLE) {
                println("$num ${src.getMeasure(num)} is $result ${tar.getMeasure(result)}\n")
                continue
            }

            println("Conversion from ${src.plural} to ${tar.plural} is impossible\n")
        } else {
            break
        }
    }
}

enum class Unit(
    private val singular: String,
    val plural: String,
    private val type: UnitType,
    private val scaleFrom: Double = 1.0,
    private val scaleTo: Double = 1.0,
) {
    UNKNOWN("???", "???", UnitType.NONE),
    METER("meter", "meters", UnitType.LENGTH),
    KILOMETER("kilometer", "kilometers", UnitType.LENGTH, 1000.0, 0.001),
    CENTIMETER("centimeter", "centimeters", UnitType.LENGTH, 0.01, 100.0),
    MILLIMETER("millimeter", "millimeters", UnitType.LENGTH, 0.001, 1000.0),
    MILE("mile", "miles", UnitType.LENGTH, 0.00062, 1609.35),
    YARD("yard", "yards", UnitType.LENGTH, 0.9144, 1.09361),
    FOOT("foot", "feet", UnitType.LENGTH, 0.3048, 3.28084),
    INCH("inch", "inches", UnitType.LENGTH, 0.0254, 39.37008),
    GRAM("gram", "grams", UnitType.WEIGHT),
    KILOGRAM("kilogram", "kilograms", UnitType.WEIGHT, 1000.0, 0.001),
    MILLIGRAM("milligram", "milligrams", UnitType.WEIGHT, 0.001, 1000.0),
    POUND("pound", "pounds", UnitType.WEIGHT, 453.592, 0.0022046),
    OUNCE("ounce", "ounces", UnitType.WEIGHT, 28.3495, 0.0352739);

    companion object {
        fun matchType(measure: String): Unit {
            return when (measure) {
                "m", "meter", "meters" -> METER
                "km", "kilometer", "kilometers" -> KILOMETER
                "cm", "centimeter", "centimeters" -> CENTIMETER
                "mm", "millimeter", "millimeters" -> MILLIMETER
                "mi", "mile", "miles" -> MILE
                "yd", "yard", "yards" -> YARD
                "ft", "foot", "feet" -> FOOT
                "in", "inch", "inches" -> INCH
                "g", "gram", "grams" -> GRAM
                "kg", "kilogram", "kilograms" -> KILOGRAM
                "mg", "milligram", "milligrams" -> MILLIGRAM
                "lb", "pound", "pounds" -> POUND
                "oz", "ounce", "ounces" -> OUNCE
                else -> UNKNOWN
            }
        }
    }

    fun getMeasure(num: Double) = if (num == 1.0) singular else plural

    operator fun get(num: Double, tar: Unit): Double {
        if (type == UnitType.NONE || tar.type == UnitType.NONE) return IMPOSSIBLE

        if (type != tar.type) return IMPOSSIBLE

        return scaleFrom * num * tar.scaleTo
    }
}

enum class UnitType { LENGTH, WEIGHT, NONE }