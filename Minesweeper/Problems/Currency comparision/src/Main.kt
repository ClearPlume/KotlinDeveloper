import java.util.*

fun main(args: Array<String>) {
    val input = Scanner(System.`in`)

    val con1 = input.next()
    val con2 = input.next()

    println(Country.same(con1, con2))
}

enum class Country(val currency: String) {
    Germany("Euro"),
    Mali("CFA franc"),
    Dominica("Eastern Caribbean dollar"),
    Canada("Canadian dollar"),
    Spain("Euro"),
    Australia("Australian dollar"),
    Brazil("Brazilian real"),
    Senegal("CFA franc"),
    France("Euro"),
    Grenada("Eastern Caribbean dollar"),
    Kiribati("Australian dollar");

    companion object {
        fun same(con1: String, con2: String): Boolean {
            if (!isNameExist(con1) || !isNameExist(con2)) {
                return false
            }
            return valueOf(con1).currency.equals(valueOf(con2).currency, true)
        }

        fun isNameExist(name: String): Boolean {
            val countryName = Array(values().size) { "" }
            var index = 0

            for (country in values()) {
                countryName[index] = country.name.toUpperCase()
                index++
            }
            return countryName.contains(name.toUpperCase())
        }
    }
}