package io.kimo.gameoflifeview.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceView
import android.view.WindowManager
import io.kimo.gameoflifeview.R
import io.kimo.gameoflifeview.game.World

/**
 * Custom surface view that displays each round of the game.
 */
class GameOfLifeView : SurfaceView, Runnable {
    private var thread: Thread? = null
    private var isRunning = false
    private var columnWidth = 1
    private var rowHeight = 1
    var numberOfColumns = 1
        private set
    var numberOfRows = 1
        private set
    private var world: World? = null
    private var allowInteraction = false
    private var proportion = DEFAULT_PROPORTION
    private var aliveColor = DEFAULT_ALIVE_COLOR
    private var deadColor = DEFAULT_DEAD_COLOR

    constructor(context: Context?) : super(context) {
        calculateWorldParams()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.GameOfLifeView, 0, 0)
        ensureCorrectAttributes(a)
        calculateWorldParams()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.GameOfLifeView, defStyle, 0)
        ensureCorrectAttributes(a)
        calculateWorldParams()
    }

    fun setAllowInteraction(allowInteraction: Boolean) {
        this.allowInteraction = allowInteraction
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        performClick()
        if (allowInteraction) {
            reviveCellsAt(event.x, event.y)
            return true
        }
        return false
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

    override fun run() {
        while (isRunning) {
            if (!holder.surface.isValid) continue
            val canvas = holder.lockCanvas()
            world!!.rotate()
            holder.unlockCanvasAndPost(drawCells(canvas))
            try {
                //Slow down the action so the changes can be seen.
                synchronized(this) { Thread.sleep(100)}
            } catch (e: InterruptedException) {
                //Ignored
            }
        }
    }

    fun setupBlinkers() {
        world!!.setupBlinkers()
    }

    fun start() {
        thread = null
        isRunning = true
        thread = Thread(this)
        thread!!.start()
    }

    fun stop() {
        isRunning = false
        while (true) {
            try {
                thread!!.join()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            break
        }
    }

    fun reviveCellsAt(x: Float, y: Float) {
        var X = (x / proportion).toInt()
        var Y = (y / proportion).toInt()
        X = Math.min(Math.max(0, X), world!!.width - 1)
        Y = Math.min(Math.max(0, Y), world!!.height - 1)
        world!!.revive(X, Y)
    }

    fun getProportion(): Int {
        return proportion
    }

    fun setProportion(proportion: Int) {
        this.proportion = proportion
        invalidate()
    }

    fun getAliveColor(): Int {
        return aliveColor
    }

    fun setAliveColor(aliveColor: Int) {
        this.aliveColor = aliveColor
        invalidate()
    }

    fun getDeadColor(): Int {
        return deadColor
    }

    fun setDeadColor(deadColor: Int) {
        this.deadColor = deadColor
        invalidate()
    }

    private fun calculateWorldParams() {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val point = Point()
        display.getSize(point)
        numberOfColumns = point.x / proportion
        numberOfRows = point.y / proportion
        columnWidth = point.x / numberOfColumns
        rowHeight = point.y / numberOfRows
        world = World(numberOfColumns, numberOfRows, true)
    }

    private fun drawCells(canvas: Canvas): Canvas {
        for (cell in world!!.cells) {
            val r = Rect()
            val p = Paint()
            if (cell!!.isAlive) {
                r[cell.x * columnWidth - 1, cell.y * rowHeight - 1, cell.x * columnWidth + columnWidth - 1] = cell.y * rowHeight + rowHeight - 1
                p.color = aliveColor
            } else {
                r[cell.x * columnWidth - 1, cell.y * rowHeight - 1, cell.x * columnWidth + columnWidth - 1] = cell.y * rowHeight + rowHeight - 1
                p.color = deadColor
            }
            canvas.drawRect(r, p)
        }
        return canvas
    }

    private fun ensureCorrectAttributes(styles: TypedArray) {

        //ensuring proportion
        val styledProportion = styles.getInt(R.styleable.GameOfLifeView_proportion, DEFAULT_PROPORTION)
        proportion = if (styledProportion > 0) {
            styledProportion
        } else {
            throw IllegalArgumentException("Proportion must be higher than 0.")
        }
        aliveColor = styles.getColor(R.styleable.GameOfLifeView_aliveCellColor, DEFAULT_ALIVE_COLOR)
        deadColor = styles.getColor(R.styleable.GameOfLifeView_deadCellColor, DEFAULT_DEAD_COLOR)
        styles.recycle()
    }

    companion object {
        const val DEFAULT_PROPORTION = 40
        const val DEFAULT_ALIVE_COLOR = Color.BLACK
        const val DEFAULT_DEAD_COLOR = Color.WHITE
    }
}