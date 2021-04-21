fun main() {
    val report = readLine()!!
    // write your code here.
    println(report.matches(Regex("\\d wrong answers?")))
}