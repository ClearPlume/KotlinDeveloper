package converter

fun main() {
    while (true) {
        val inputPattern =
            Regex("(-?\\d+(?:\\.\\d+)?) (degree(?:s)? [a-z]+|[a-z]+) ([a-z]+) (degree(?:s)? [a-z]+|[a-z]+)").toPattern()

        print("Enter what you want to convert (or exit): ")
        val input = readLine()!!.toLowerCase()
        var result: Double

        if (input != "exit") {
            val inputMatcher = inputPattern.matcher(input)

            if (inputMatcher.find()) {
                val num = inputMatcher.group(1).toDouble()
                val src = Unit.matchType(inputMatcher.group(2))
                val tar = Unit.matchType(inputMatcher.group(4))

                if (src.type == UnitType.NONE || tar.type == UnitType.NONE || src.type != tar.type) {
                    println("Conversion from ${src.plural} to ${tar.plural} is impossible\n")
                    continue
                }

                if (num < 0 && src.type != UnitType.TEMPERATURE) {
                    println("${src.type} shouldn't be negative")
                    continue
                }

                result = src[num, tar]
                println("$num ${src.getMeasure(num)} is $result ${tar.getMeasure(result)}\n")
            } else {
                println("Parse error\n")
            }
        } else {
            break
        }
    }
}

enum class Unit(
    private val singular: String,
    val plural: String,
    val type: UnitType,
    private val scaleFrom: (Double) -> Double = { it },
    private val scaleTo: (Double) -> Double = { it },
) {
    UNKNOWN("???", "???", UnitType.NONE),
    METER("meter", "meters", UnitType.LENGTH),
    KILOMETER("kilometer", "kilometers", UnitType.LENGTH, { it * 1000.0 }, { it * 0.001 }),
    CENTIMETER("centimeter", "centimeters", UnitType.LENGTH, { it * 0.01 }, { it * 100.0 }),
    MILLIMETER("millimeter", "millimeters", UnitType.LENGTH, { it * 0.001 }, { it * 1000.0 }),
    MILE("mile", "miles", UnitType.LENGTH, { it * 0.00062 }, { it * 1609.35 }),
    YARD("yard", "yards", UnitType.LENGTH, { it * 0.9144 }, { it * 1.09361 }),
    FOOT("foot", "feet", UnitType.LENGTH, { it * 0.3048 }, { it * 3.28084 }),
    INCH("inch", "inches", UnitType.LENGTH, { it * 0.0254 }, { it * 39.37008 }),
    GRAM("gram", "grams", UnitType.WEIGHT),
    KILOGRAM("kilogram", "kilograms", UnitType.WEIGHT, { it * 1000.0 }, { it * 0.001 }),
    MILLIGRAM("milligram", "milligrams", UnitType.WEIGHT, { it * 0.001 }, { it * 1000.0 }),
    POUND("pound", "pounds", UnitType.WEIGHT, { it * 453.592 }, { it * 0.0022046 }),
    OUNCE("ounce", "ounces", UnitType.WEIGHT, { it * 28.3495 }, { it * 0.0352739 }),
    CELSIUS("degree Celsius", "degrees Celsius", UnitType.TEMPERATURE),
    FAHRENHEIT(
        "degree Fahrenheit",
        "degrees Fahrenheit",
        UnitType.TEMPERATURE,
        { (it - 32) * 5 / 9 },
        { it * 9 / 5 + 32 }),
    KELVIN("kelvin", "kelvins", UnitType.TEMPERATURE, { it - 273.15 }, { it + 273.15 });

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
                "c", "dc", "degree celsius", "degrees celsius", "celsius" -> CELSIUS
                "f", "df", "degree fahrenheit", "degrees fahrenheit", "fahrenheit" -> FAHRENHEIT
                "k", "kelvin", "kelvins" -> KELVIN
                else -> UNKNOWN
            }
        }
    }

    fun getMeasure(num: Double) = if (num == 1.0) singular else plural

    operator fun get(num: Double, tar: Unit): Double = tar.scaleTo.invoke(scaleFrom.invoke(num))
}

enum class UnitType {
    LENGTH, WEIGHT, TEMPERATURE, NONE;

    override fun toString(): String {
        val str = super.toString().toLowerCase()
        return if (str == "none") "???" else str
    }
}