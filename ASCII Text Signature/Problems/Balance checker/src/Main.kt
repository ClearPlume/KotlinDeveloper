import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    var initial = scanner.nextInt()
    var purchase = 0
    var enough = true

    while (scanner.hasNextInt()) {
        purchase = scanner.nextInt()
        if (initial >= purchase) {
            initial -= purchase
        } else {
            enough = false
            break
        }
    }

    if (enough) {
        print("Thank you for choosing us to manage your account! You have $initial money.")
    } else {
        print("Error, insufficient funds for the purchase. You have $initial, but you need $purchase.")
    }
}