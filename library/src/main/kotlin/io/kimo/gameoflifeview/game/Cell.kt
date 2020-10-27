package io.kimo.gameoflifeview.game

/**
 * Class that represents a single cell in the game.
 */
class Cell {
    @JvmField
    val x: Int
    @JvmField
    val y: Int
    @JvmField
    var isAlive: Boolean

    constructor() {
        x = 0
        y = 0
        isAlive = true
    }

    constructor(x: Int, y: Int) {
        this.x = x
        this.y = y
        isAlive = true
    }

    constructor(x: Int, y: Int, status: Boolean) {
        this.x = x
        this.y = y
        isAlive = status
    }

    override fun hashCode(): Int {
        return this.toString().hashCode()
    }

    override fun equals(otherCell: Any?): Boolean {
        return if (otherCell == null) false else if (otherCell === this) true else if (otherCell !is Cell) false else {
            this.hashCode() == otherCell.hashCode()
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