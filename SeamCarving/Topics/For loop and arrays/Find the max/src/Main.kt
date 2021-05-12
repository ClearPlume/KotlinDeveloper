import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val nums = IntArray(scanner.nextInt())

    for (i in nums.indices) {
        nums[i] = scanner.nextInt()
    }

    println(nums.indexOf(nums.max()!!))
}