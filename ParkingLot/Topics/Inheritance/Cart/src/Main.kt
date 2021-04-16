fun main() {
    val productType = readLine()!!
    val price = readLine()!!.toInt()
    val product = ProductFactory.createProduct(price, productType)
    println(product?.totalPrice())
}

class ProductFactory {
    companion object {
        fun createProduct(price: Int, type: String): Product? {
            return when (type) {
                "headphones" -> HeadPhone(price)
                "smartphone" -> SmartPhone(price)
                "tv" -> TV(price)
                "laptop" -> LapTop(price)
                else -> null
            }
        }
    }
}

open class Product(private val price: Int) {
    open val tax = -1.0

    fun totalPrice() = (price + price * tax).toInt()
}

class HeadPhone(price: Int) : Product(price) {
    override val tax: Double
        get() = 0.11
}

class SmartPhone(price: Int) : Product(price) {
    override val tax: Double
        get() = 0.15
}

class TV(price: Int) : Product(price) {
    override val tax: Double
        get() = 0.17
}

class LapTop(price: Int) : Product(price) {
    override val tax: Double
        get() = 0.19
}
