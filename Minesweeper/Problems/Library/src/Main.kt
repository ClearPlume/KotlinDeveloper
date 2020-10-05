object Math {
    fun abs(value: Int): Int {
        return if (value > 0) value else if (value < 0) -value else 0
    }

    fun abs(value: Double): Double {
        return if (value > 0) value else if (value < 0) -value else 0.0
    }
}