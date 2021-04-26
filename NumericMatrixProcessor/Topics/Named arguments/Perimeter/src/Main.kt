import kotlin.math.hypot

// Because the number of parameters is uncertain, vararg is used to receive parameters
fun perimeter(vararg coordinates: Number): Double {
    // In order to avoid the ambiguity of understanding, "perimeter" is abbreviated as "per"
    var per = 0.0

    // The two parameters represent a point, so the parameters are traversed in units of points
    for (curPoint in 1..coordinates.size / 2) {
        // "nextPoint" is "curPoint" + 1 if "curPoint" is not the last point; otherwise, "nextPoint" is 1
        val nextPoint = if (curPoint < coordinates.size / 2) curPoint + 1 else 1

        /*
         * It can be inferred from the following corresponding relations:
         * curPoint  point.x  point.y
         *    1         0        1
         *    2         2        3
         *    3         4        5
         */
        per += hypot(
            coordinates[nextPoint * 2 - 2].castDouble() - coordinates[curPoint * 2 - 2].castDouble(),
            coordinates[nextPoint * 2 - 1].castDouble() - coordinates[curPoint * 2 - 1].castDouble()
        )
    }
    return per
}

/*
 *     I'm not sure what type of parameters are passed in (I used Int to receive the parameters at first,
 * but the compilation failed...), so I simply use Number to receive them, and then extend a method for
 * Number to convert Number into Double
 */
fun Number.castDouble(): Double {
    if (this is Double) {
        return this
    } else {
        return this.toDouble()
    }
}