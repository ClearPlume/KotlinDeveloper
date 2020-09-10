import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.max

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val numOfNumber = scanner.nextInt()

    if (numOfNumber > 1) {
        var maxProduct = 0
        val nums = ArrayList<Int>(numOfNumber)
        scanner.skip("\n")
        scanner.nextLine().split(" ").forEach { value -> nums.add(value.toInt()) }

        for (i in 1 until numOfNumber) {
            for (j in 0 until i) {
                maxProduct = max(maxProduct, nums[i] * nums[j])
            }
        }

        println(maxProduct)
    } else if (numOfNumber == 1) {
        println(scanner.nextInt())
    } else {
        println(0)
    }
}