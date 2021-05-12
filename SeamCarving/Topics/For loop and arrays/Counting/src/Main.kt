import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val nums = IntArray(scanner.nextInt())

    for (i in nums.indices) {
        nums[i] = scanner.nextInt()
    }

    val m = scanner.nextInt()
    var time = 0

    for (num in nums) {
        if (num == m) {
            time++
        }
    }

    println(time)
}