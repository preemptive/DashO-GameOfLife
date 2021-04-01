package io.kimo.gameoflifeview.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceView
import android.view.WindowManager
import io.kimo.gameoflifeview.Death
import io.kimo.gameoflifeview.Life
import io.kimo.gameoflifeview.R
import io.kimo.gameoflifeview.game.World

/**
 * Custom surface view that displays each round of the game.
 */
@Life
@Death
class GameOfLifeView : SurfaceView, Runnable {
    private var thread: Thread? = null
    private var isRunning = false
    private var columnWidth = 1
    private var rowHeight = 1
    private var numberOfColumns = 1
    private var numberOfRows = 1
    private val world: World
    private var allowInteraction = false
    private var proportion = DEFAULT_PROPORTION
    private var aliveColor = DEFAULT_ALIVE_COLOR
    private var deadColor = DEFAULT_DEAD_COLOR

    constructor(context: Context?) : super(context) {
        world = calculateWorldParams()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.GameOfLifeView, 0, 0)
        ensureCorrectAttributes(a)
        world = calculateWorldParams()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.GameOfLifeView, defStyle, 0)
        ensureCorrectAttributes(a)
        world = calculateWorldParams()
    }

    @Suppress("unused")
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

    @Suppress("RedundantOverride")
    override fun performClick(): Boolean {
        return super.performClick()
    }

    override fun run() {
        while (isRunning) {
            if (!holder.surface.isValid) continue
            val canvas = holder.lockCanvas()
            world.rotate()
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
        world.setupBlinkers()
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

    private fun reviveCellsAt(coordinateX: Float, coordinateY: Float) {
        var x = (coordinateX / proportion).toInt()
        var y = (coordinateY / proportion).toInt()
        x = 0.coerceAtLeast(x).coerceAtMost(world.width - 1)
        y = 0.coerceAtLeast(y).coerceAtMost(world.height - 1)
        world.revive(x, y)
    }

    @Suppress("unused")
    fun getProportion(): Int {
        return proportion
    }

    @Suppress("unused")
    fun setProportion(proportion: Int) {
        this.proportion = proportion
        invalidate()
    }

    @Suppress("unused")
    fun getAliveColor(): Int {
        return aliveColor
    }

    @Suppress("unused")
    fun setAliveColor(aliveColor: Int) {
        this.aliveColor = aliveColor
        invalidate()
    }

    @Suppress("unused")
    fun getDeadColor(): Int {
        return deadColor
    }

    @Suppress("unused")
    fun setDeadColor(deadColor: Int) {
        this.deadColor = deadColor
        invalidate()
    }

    private fun calculateWorldParams() : World {
        val displayMetrics = resources.displayMetrics
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            @Suppress("DEPRECATION")
            wm.defaultDisplay?.getRealMetrics(displayMetrics)
        }
        numberOfColumns = displayMetrics.widthPixels / proportion
        numberOfRows = displayMetrics.heightPixels / proportion
        columnWidth = displayMetrics.widthPixels / numberOfColumns
        rowHeight = displayMetrics.heightPixels / numberOfRows
        return World(numberOfColumns, numberOfRows, true)
    }

    private fun drawCells(canvas: Canvas): Canvas {
        for (cell in world.cells) {
            val r = Rect()
            val p = Paint()
            if (cell.isAlive) {
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