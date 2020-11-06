package rules

import io.kimo.gameoflifeview.game.World
import junit.framework.TestCase
import org.junit.Assert
import org.junit.Test

/**
 * Rule #4: Any dead cell with exactly three live neighbours becomes a live
 * cell, as if by reproduction.
 */
class Rule4Test : TestCase() {
    @Test
    fun testDeadCellsWith3LiveNeighboursWillRevive() {
        val world = World(3, 3)
        world.kill(1, 2)
        world.kill(2, 2)
        world.kill(2, 1)
        world.kill(2, 0)
        world.kill(1, 0)
        world.kill(1, 1)
        world.rotate()
        Assert.assertTrue(world[1, 1].isAlive)
    }
}