fun bill(priceList: Map<String, Int>, shoppingList: Array<String>): Int {
    // put your code here
    var price = 0

    for (shop in shoppingList) {
        price += priceList[shop] ?: 0
    }

    return price
}