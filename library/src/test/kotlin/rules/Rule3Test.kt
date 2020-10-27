package rules

import io.kimo.gameoflifeview.game.World
import junit.framework.TestCase
import org.junit.Assert
import org.junit.Test

/**
 * Rule #3: Any live cell with more than three live neighbours dies, as if
 * by overcrowding.
 */
class Rule3Test : TestCase() {
    @Test
    fun testCellsWith4LiveNeighboursWillDie() {
        val world = World(3, 3)
        world.kill(0, 2)
        world.kill(1, 2)
        world.kill(2, 2)
        world.kill(2, 0)
        world.rotate()
        Assert.assertFalse(world[1, 1]!!.isAlive)
    }

    @Test
    fun testCellsSurroundedByLiveNeighboursWillDie() {
        val world = World(3, 3)
        world.rotate()
        Assert.assertFalse(world[1, 1]!!.isAlive)
    }
}