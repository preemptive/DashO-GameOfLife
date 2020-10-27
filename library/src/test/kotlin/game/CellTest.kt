package game

import io.kimo.gameoflifeview.game.Cell
import junit.framework.TestCase
import org.junit.Assert
import org.junit.Test

class CellTest : TestCase() {
    @Test
    fun testEquals() {
        val cell_1 = Cell(1, 2)
        val cell_2 = Cell(1, 2)
        Assert.assertEquals("Must be true if cells have same coordinates", true, cell_1.equals(cell_2))
    }

    @Test
    fun testCoordinates() {
        val cell = Cell()
        Assert.assertNotNull("Must have the X coordinate", cell.x)
        Assert.assertNotNull("Must have the Y coordinate", cell.y)
    }

    @Test
    fun testDie() {
        val cell = Cell()
        Assert.assertTrue("it was alive", cell.isAlive)
        cell.die()
        Assert.assertFalse("should be dead", cell.isAlive)
    }

    @Test
    fun testReborn() {
        val cell = Cell()
        cell.die()
        Assert.assertFalse("kill cell", cell.isAlive)
        cell.reborn()
        Assert.assertTrue("it must reborn", cell.isAlive)
    }
}