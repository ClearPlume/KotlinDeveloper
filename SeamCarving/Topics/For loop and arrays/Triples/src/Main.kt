import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val nums = IntArray(scanner.nextInt())

    for (i in nums.indices) {
        nums[i] = scanner.nextInt()
    }

    var triples = 0

    for (num in nums) {
        val curNumIndex = nums.indexOf(num)
        if (curNumIndex < nums.size - 2) {
            if (num + 1 == nums[curNumIndex + 1] && num + 2 == nums[curNumIndex + 2]) {
                triples++
            }
        }
    }

    println(triples)
}