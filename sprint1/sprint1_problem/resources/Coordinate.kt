class Coordinate(Xc: Int, Yc: Int) {
    val x: Int = Xc
    val y: Int = Yc

    fun equals(other: Coordinate): Boolean {
        return this.x == other.x && this.y == other.y
    }
}