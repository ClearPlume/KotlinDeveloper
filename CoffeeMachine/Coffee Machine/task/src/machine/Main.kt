package machine

import java.util.*

fun main() {
    print("Write how many cups of coffee you will need:")
    val cup = Scanner(System.`in`).nextInt()

    println(
        """
        For $cup cups of coffee you will need:
        ${cup * 200} ml of water
        ${cup * 50} ml of milk
        ${cup * 15} g of coffee beans
    """.trimIndent()
    )
}
