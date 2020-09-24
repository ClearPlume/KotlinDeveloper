import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val nums = IntArray(scanner.nextInt())

    for (i in nums.indices) {
        nums[i] = scanner.nextInt()
    }

    var repeatTime = scanner.nextInt()

    repeatTime %= nums.size

    for (i in 1..repeatTime) {
        rotate(nums)
    }

    println(nums.joinToString(" "))
}

fun rotate(nums: IntArray) {
    val lastVal = nums.last()

    for (i in nums.lastIndex downTo 1) {
        nums[i] = nums[i - 1]
    }

    nums[0] = lastVal
}