fun main(args: Array<String>) {
    // write your code here
    val speed = readLine()!!.toInt()
    val limit = readLine()!!

    println(if (limit != "no limit") testLimit(speed, limit.toInt()) else testLimit(speed))
}

fun testLimit(speed: Int, limit: Int = 60): String {
    if (speed <= limit) return "Within the limit"

    return "Exceeds the limit by ${speed - limit} kilometers per hour"
}