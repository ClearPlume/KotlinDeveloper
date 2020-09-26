fun Fridge.take(productName: String): Product {
    open()
    val pro = find(productName)
    close()
    return pro
}