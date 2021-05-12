import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val companyNum = scanner.nextInt()
    val incomes = IntArray(companyNum)
    val taxes = IntArray(companyNum)
    val totalTaxes = IntArray(companyNum)

    for (i in 0 until companyNum) {
        incomes[i] = scanner.nextInt()
    }
    for (i in 0 until companyNum) {
        taxes[i] = scanner.nextInt()
    }
    for (i in 0 until companyNum) {
        totalTaxes[i] = incomes[i] * taxes[i]
    }

    println(totalTaxes.indexOf(totalTaxes.max()!!) + 1)
}