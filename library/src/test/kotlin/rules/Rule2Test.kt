package rules

import io.kimo.gameoflifeview.game.World
import junit.framework.TestCase
import org.junit.Assert
import org.junit.Test

/**
 * Rule #2: Any live cell with two or three live neighbours lives on to the
 * next generation.
 */
class Rule2Test : TestCase() {
    @Test
    fun testCellsWith2LiveNeighboursWillLive() {
        val world = World(3, 3)
        world.kill(0, 2)
        world.kill(1, 2)
        world.kill(2, 2)
        world.kill(0, 0)
        world.kill(1, 0)
        world.kill(2, 0)
        world.rotate()
        Assert.assertTrue(world[1, 1]!!.isAlive)
    }

    @Test
    fun testCellsWith3LiveNeighboursWillLive() {
        val world = World(3, 3)
        world.kill(1, 1)
        world.kill(2, 1)
        world.kill(1, 0)
        world.kill(2, 0)
        world.rotate()
        Assert.assertTrue(world[0, 2]!!.isAlive)
    }
}