package rules

import io.kimo.gameoflifeview.game.Cell
import io.kimo.gameoflifeview.game.World
import junit.framework.TestCase
import org.junit.Assert
import org.junit.Test

/**
 * Rule #1: Any live cell with fewer than two live neighbours dies, as if caused by
 * under-population.
 */
class Rule1Test : TestCase() {
    @Test
    fun testCellsWithOneNeighboursShouldDie() {
        val world = World(2, 2)
        world.kill(1, 1)
        world.kill(0, 0)
        world.rotate()
        val expectedCells = arrayOf(Cell(0, 0, false), Cell(0, 1, false),
                Cell(1, 0, false), Cell(1, 1, false))
        Assert.assertArrayEquals(expectedCells, world.cells)
    }

    @Test
    fun testCellsWithNoLiveNeighboursShouldDie() {
        val world = World(3, 3)
        world.kill(0, 0)
        world.kill(1, 0)
        world.kill(2, 0)
        world.kill(0, 1)
        world.kill(2, 1)
        world.kill(0, 2)
        world.kill(1, 2)
        world.kill(2, 2)
        world.rotate()
        Assert.assertFalse(world[1, 1]!!.isAlive)
    }
}