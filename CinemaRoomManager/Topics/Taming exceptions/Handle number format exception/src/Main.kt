fun parseCardNumber(cardNumber: String): Long {
    val cardRegex = Regex("(\\d{4}\\s){3}\\d{4}")

    if (!cardNumber.matches(cardRegex)) {
        throw NumberFormatException("Card number is incorrect!")
    }

    return cardNumber.replace(" ", "").toLong()
}