import kotlin.random.Random

fun generateTemperature(seed: Int): String {
    // write your code here
    val randomizer = Random(seed)
    val temperatures = Array(7) { randomizer.nextInt(20, 31) }

    return temperatures.joinToString(" ")
}