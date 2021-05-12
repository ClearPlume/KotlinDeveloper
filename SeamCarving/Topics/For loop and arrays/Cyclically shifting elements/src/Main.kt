import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val nums = IntArray(scanner.nextInt())

    for (i in nums.indices) {
        nums[i] = scanner.nextInt()
    }

    print(nums.last())

    for (i in 0 until nums.size - 1) {
        print(" ${nums[i]}")
    }
}