package io.kimo.gameoflifeview.game

import java.util.*

/**
 * Class that represents the world of cells.
 */
class World(val width: Int, val height: Int, random: Boolean = false) {
    var cells: Array<Cell>
        private set
    val board: Array<Array<Cell>>

    init {
        cells = mutableListOf<Cell>().toTypedArray()
        val nullCell = Cell()
        board = Array(this.width) { Array(this.height) { nullCell } }
        setup(random)
    }

    private fun setup(random: Boolean) {
        for (i in 0 until width) for (j in 0 until height) if (random) board[i][j] = Cell(i, j, Random().nextBoolean()) else board[i][j] = Cell(i, j)
        updateCells()
    }

    private fun updateCells() {
        val boardCells: MutableList<Cell> = ArrayList()
        for (i in 0 until width) for (j in 0 until height) boardCells.add(board[i][j])
        cells = boardCells.toTypedArray()
    }

    operator fun get(i: Int, j: Int): Cell {
        return board[i][j]
    }

    fun setupBlinkers() {
        for (i in 0 until width) {
            for (j in 0 until height) {
                kill(i, j)
            }
        }
        var count = 0
        var i = 2
        while (i < width - 1) {
            var j = 2
            while (j < height - 1) {
                revive(i, j)
                if (count++ % 2 == 0) {
                    revive(i - 1, j)
                    revive(i + 1, j)
                } else {
                    revive(i, j - 1)
                    revive(i, j + 1)
                }
                j += 4
            }
            i += 4
        }
    }

    fun kill(i: Int, j: Int) {
        board[i][j].die()
    }

    fun revive(i: Int, j: Int) {
        board[i][j].reborn()
    }

    fun liveNeighboursOf(i: Int, j: Int): Array<Cell> {
        val liveNeighbours: MutableList<Cell> = ArrayList()

        //up-left
        if (i - 1 >= 0 && j + 1 <= height - 1) {
            val neighbour = get(i - 1, j + 1)
            if (neighbour.isAlive) liveNeighbours.add(neighbour)
        }

        //up
        if (j + 1 <= height - 1) {
            val neighbour = get(i, j + 1)
            if (neighbour.isAlive) liveNeighbours.add(neighbour)
        }

        //up-right
        if (i + 1 <= width - 1 && j + 1 <= height - 1) {
            val neighbour = get(i + 1, j + 1)
            if (neighbour.isAlive) liveNeighbours.add(neighbour)
        }

        //left
        if (i - 1 >= 0) {
            val neighbour = get(i - 1, j)
            if (neighbour.isAlive) liveNeighbours.add(neighbour)
        }

        //right
        if (i + 1 <= width - 1) {
            val neighbour = get(i + 1, j)
            if (neighbour.isAlive) liveNeighbours.add(neighbour)
        }

        //down-left
        if (i - 1 >= 0 && j - 1 >= 0) {
            val neighbour = get(i - 1, j - 1)
            if (neighbour.isAlive) liveNeighbours.add(neighbour)
        }

        //down
        if (j - 1 >= 0) {
            val neighbour = get(i, j - 1)
            if (neighbour.isAlive) liveNeighbours.add(neighbour)
        }

        //down-right
        if (i + 1 <= width - 1 && j - 1 >= 0) {
            val neighbour = get(i + 1, j - 1)
            if (neighbour.isAlive) liveNeighbours.add(neighbour)
        }
        return liveNeighbours.toTypedArray()
    }

    fun rotate() {
        val futureLiveCells: MutableList<Cell?> = ArrayList()
        val futureDeadCells: MutableList<Cell?> = ArrayList()
        for (cell in cells) {
            val neighbours = liveNeighboursOf(cell.x, cell.y)
            if (rule1(cell, neighbours)) futureDeadCells.add(cell)
            if (rule2(cell, neighbours)) futureLiveCells.add(cell)
            if (rule3(cell, neighbours)) futureDeadCells.add(cell)
            if (rule4(cell, neighbours)) futureLiveCells.add(cell)
        }
        updateBoard(futureLiveCells, futureDeadCells)
        updateCells()
    }

    private fun updateBoard(lives: List<Cell?>, deads: List<Cell?>) {
        for (cell in lives) revive(cell!!.x, cell.y)
        for (cell in deads) kill(cell!!.x, cell.y)
    }

    private fun rule1(c: Cell, n: Array<Cell>): Boolean {
        return c.isAlive && n.size < 2
    }

    private fun rule2(c: Cell, n: Array<Cell>): Boolean {
        return c.isAlive && (n.size == 3 || n.size == 2)
    }

    private fun rule3(c: Cell, n: Array<Cell>): Boolean {
        return c.isAlive && n.size > 3
    }

    private fun rule4(c: Cell, n: Array<Cell>): Boolean {
        return !c.isAlive && n.size == 3
    }
}