import java.util.Scanner

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val a = scanner.nextInt()
    val b = scanner.nextInt()
    val c = scanner.nextInt()

    val aGreatThan0 = a > 0
    val bGreatThan0 = b > 0
    val cGreatThan0 = c > 0

    println(aGreatThan0 && !bGreatThan0 && !cGreatThan0 ||
            !aGreatThan0 && bGreatThan0 && !cGreatThan0 ||
            !aGreatThan0 && !bGreatThan0 && cGreatThan0)
}