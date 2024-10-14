package com.sb.moovich.core.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.core.graphics.component1
import androidx.core.graphics.component2
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.sb.moovich.core.R
import java.io.ByteArrayInputStream
import java.io.FileDescriptor
import java.io.FileInputStream
import java.io.InputStream
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin
import kotlin.math.tan
import kotlin.math.tanh

class MicrophoneView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = context.getColor(R.color.secondary)
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val points = mutableListOf<PointF>()
    private val basePoints = mutableListOf<PointF>()

    private val pointCount = 12

    private val rmsHistory = FloatArray(5)
    private var rmsIndex = 0

    var rms = 0f
        set(value) {
            field = value
            updateRmsHistory(field)
            distortPoints()
            invalidate()
        }

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)
        initializeBasePoints()
    }

    private fun updateRmsHistory(newRms: Float) {
        rmsHistory[rmsIndex] = newRms
        rmsIndex = (rmsIndex + 1) % rmsHistory.size
    }

    private fun initializeBasePoints() {
        basePoints.clear()
        points.clear()

        val centerX = width / 2f
        val centerY = height / 2f
        val radius = min(centerX, centerY) * 0.8f

        for (i in 0 until pointCount) {
            val angle = 2 * Math.PI * i / pointCount
            val x = centerX + (radius * cos(angle)).toFloat()
            val y = centerY + (radius * sin(angle)).toFloat()

            basePoints.add(PointF(x, y))
            points.add(PointF(x, y))
        }
    }

    private fun getAverageRms(): Float {
        return rmsHistory.average().toFloat()
    }

    private fun distortPoints() {
        val averageRms = getAverageRms()
        for (i in points.indices) {
            val baseX = basePoints[i].x
            val baseY = basePoints[i].y
            val maxDistortion = 20 * averageRms // Максимальное отклонение

            val offsetX = (Math.random().toFloat() - 0.5f) * maxDistortion
            val offsetY = (Math.random().toFloat() - 0.5f) * maxDistortion

            val interpolationFactor = 0.2f
            points[i] = PointF(
                points[i].x + (baseX + offsetX - points[i].x) * interpolationFactor,
                points[i].y + (baseY + offsetY - points[i].y) * interpolationFactor
            )
        }
    }

    @SuppressLint("DrawAllocation", "ResourceType")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val centerX = width / 2f
        val centerY = height / 2f
        val radius = min(centerX, centerY) * 0.8f
        val path = Path()
        path.moveTo(points[0].x, points[0].y)
        var startAngle = 0.0
        var endAngle = Math.PI / 6

        for (i in 0 until pointCount) { // circle with cubic Bézier curves
            val (startX, startY) = points[i]
            val (endX, endY) = points[(i + 1) % points.size]

            val alpha = (4 / 3.0) * tan((endAngle - startAngle) / 4)

            val control1X = (startX - alpha * radius * sin(startAngle)).toFloat()
            val control1Y = (startY + alpha * radius * cos(startAngle)).toFloat()
            val control2X = (endX + alpha * radius * sin(endAngle)).toFloat()
            val control2Y = (endY - alpha * radius * cos(endAngle)).toFloat()

            path.apply {
                moveTo(startX, startY)
                cubicTo(control1X, control1Y, control2X, control2Y, endX, endY)
            }
            startAngle = endAngle
            endAngle += Math.PI / 6
        }

        path.close()
        canvas.drawPath(path, paint)
        val fillPath = Path()
        fillPath.moveTo(points[0].x, points[0].y)
        for (i in points) {
            fillPath.lineTo(i.x, i.y)
        }
        canvas.drawPath(fillPath, paint)

        val icon = VectorDrawableCompat.createFromXml(
            context.resources,
            context.resources.getXml(R.drawable.ic_micro)
        )
        icon.setTint(context.getColor(R.color.white))
        val padding = 0.3
        icon.setBounds(
            0 + (width * padding).toInt(),
            0 + (height * padding).toInt(),
            width - (width * padding).toInt(),
            height - (height * padding).toInt()
        )
        icon.draw(canvas)
    }
}
