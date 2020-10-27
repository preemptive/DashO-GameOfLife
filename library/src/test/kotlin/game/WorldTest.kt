package game

import io.kimo.gameoflifeview.game.Cell
import io.kimo.gameoflifeview.game.World
import junit.framework.TestCase
import org.junit.Assert
import org.junit.Test

class WorldTest : TestCase() {
    @Test
    fun testCountTheLiveNeighbours() {
        val world = World(3, 3)
        world.kill(0, 0)
        world.kill(0, 1)
        Assert.assertEquals(6, world.liveNeighboursOf(1, 1).size)
    }

    @Test
    fun testReviveCell() {
        val world = World(2, 2)
        world.kill(1, 0)
        Assert.assertFalse(world[1, 0]!!.isAlive)
        world.revive(1, 0)
        Assert.assertTrue(world[1, 0]!!.isAlive)
    }

    @Test
    fun testKillCell() {
        val world = World(2, 2)
        val expectedCell = Cell(1, 0, false)
        world.kill(1, 0)
        Assert.assertEquals(expectedCell, world[1, 0])
    }

    @Test
    fun testGetCell() {
        val world = World(2, 2)
        val expectedCell = Cell(1, 1)
        Assert.assertEquals(expectedCell, world[1, 1])
    }

    @Test
    fun testSetup() {
        val world = World(3, 3)
        assertNotNull(world.cells)
        assertNotNull(world.board)
    }

    @Test
    fun testIntegrityOfCells() {
        val world = World(2, 2)
        val firstCell = Cell(0, 0)
        val secondCell = Cell(0, 1)
        val thirdCell = Cell(1, 0)
        val fourthCell = Cell(1, 1)
        val expectedCells = arrayOf(firstCell, secondCell, thirdCell, fourthCell)
        Assert.assertArrayEquals(expectedCells, world.cells)
    }

    @Test
    fun testWorldDimensions() {
        val width = 3
        val height = 3
        val world = World(width, height)
        assertEquals(width, world.width)
        assertEquals(height, world.height)
    }

    @Test
    fun testNumberOfCells() {
        val world = World(3, 3)
        val expectedNumberOfCells = 9
        assertEquals(expectedNumberOfCells, world.cells.size)
    }
}