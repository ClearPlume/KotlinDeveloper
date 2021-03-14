data class Player(val id: Int, val name: String, val hp: Int) {
    companion object {
        var id = 0

        fun create(name: String) = Player(id++, name, 100)
    }
}