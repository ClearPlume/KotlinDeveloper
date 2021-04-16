fun identity(value: Int) = value

fun half(value: Int) = value / 2

fun zero(value: Int) = 0

fun generate(functionName: String): (Int) -> Int {
    return when (functionName) {
        "identity" -> ::identity
        "half" -> ::half
        else -> ::zero
    }
}