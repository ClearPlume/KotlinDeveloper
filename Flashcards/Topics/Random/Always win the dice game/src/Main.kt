import kotlin.random.Random

fun createDiceGameRandomizer(n: Int): Int {
    // write your code here
    var our = 0
    var they = 0
    var seed: Int

    do {
        seed = Random.nextInt()
        val randomizer = Random(seed)

        repeat(n) {
            they += randomizer.nextInt(1, 7)
        }
        repeat(n) {
            our += randomizer.nextInt(1, 7)
        }
    } while (our < they)

    return seed
}