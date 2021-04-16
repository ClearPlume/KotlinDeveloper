fun main() {
    val rooms = clamp(readLine()!!.toInt(), 1, 10)
    val price = clamp(readLine()!!.toInt(), 0, 1_000_000)
    val house = HouseFactory.createHouse(rooms, price)
    println(house?.totalPrice())
}

fun clamp(num: Int, min: Int, max: Int): Int {
    return when {
        num < min -> min
        num > max -> max
        else -> num
    }
}

class HouseFactory {
    companion object {
        fun createHouse(rooms: Int, price: Int): House? {
            return when (rooms) {
                1 -> Cabin(price)
                2, 3 -> Bungalow(price)
                4 -> Cottage(price)
                in 5..7 -> Mansion(price)
                in 8..10 -> Palace(price)
                else -> null
            }
        }
    }
}

open class House(private val price: Int) {
    open val coefficient = 1.0

    fun totalPrice(): Int {
        return (price * coefficient).toInt()
    }
}

class Cabin(price: Int) : House(price)

class Bungalow(price: Int) : House(price) {
    override val coefficient: Double
        get() = 1.2
}

class Cottage(price: Int) : House(price) {
    override val coefficient: Double
        get() = 1.25
}

class Mansion(price: Int) : House(price) {
    override val coefficient: Double
        get() = 1.4
}

class Palace(price: Int) : House(price) {
    override val coefficient: Double
        get() = 1.6
}