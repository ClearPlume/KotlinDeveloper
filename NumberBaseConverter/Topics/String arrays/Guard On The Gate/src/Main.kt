fun main() {
    val backFromTheWall = readLine()!!.split(',').map { it }.toTypedArray()
    val returnedWatchman = readLine()!!.toString()
    // do not touch the lines above
    // write your code here
    val names = backFromTheWall.toMutableList()
    names.add(returnedWatchman)

    println(names.joinToString(", "))
}
