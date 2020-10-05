enum class DangerLevel(val dLevel: Int) {
    HIGH(3), MEDIUM(2), LOW(1);

    fun getLevel() = dLevel
}