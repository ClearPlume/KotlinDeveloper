data class Block(val color: String) {
    object DimProperties {
        var length = 2
        var width = 2

        fun blocksInBox(length: Int, width: Int) = length / this.length * (width / this.width)
    }
}