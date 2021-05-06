import kotlin.random.Random

fun generatePredictablePassword(seed: Int): String {
    var randomPassword = ""
    // write your code here
    val randomizer = Random(seed)

    repeat(10) {
        randomPassword += randomizer.nextInt(33, 127).toChar()
    }

    return randomPassword
}