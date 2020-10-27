package io.kimo.gameoflifeview.game

/**
 * Class that represents a single cell in the game.
 */
class Cell(@JvmField val x: Int = 0, @JvmField val y: Int = 0, status: Boolean = true) {
    @JvmField
    var isAlive: Boolean = status


    override fun hashCode(): Int {
        return this.toString().hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return if (other == null) false else if (other === this) true else if (other !is Cell) false else {
            this.hashCode() == other.hashCode()
        }
    }

    fun die() {
        isAlive = false
    }

    fun reborn() {
        isAlive = true
    }

    override fun toString(): String {
        return "(" + x + "," + y + ")"
    }
}