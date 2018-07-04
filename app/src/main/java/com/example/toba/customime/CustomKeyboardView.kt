package com.example.toba.customime

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.LayoutInflater
import java.util.jar.Attributes

class CustomKeyboardView(context: Context, attrs: AttributeSet) : KeyboardView(context, attrs) {

    private lateinit var key_0: Keyboard.Key

    var indicator: BitmapDrawable? = null

    private lateinit var onCloseListener: () -> Unit

    override fun setKeyboard(keyboard: Keyboard) {
        super.setKeyboard(keyboard)
        //key_0 = keyboard.keys[1]
    }

    override fun closing() {
        onCloseListener()
        super.closing()
    }

    fun setOnCloseListener(lambda: () -> Unit) {
        onCloseListener = lambda
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        indicator?.draw(canvas)

        // 一番最初のキーを常に白く塗りつぶす
        //val x = key_0.x + x.toInt()
        //val y = key_0.y + y.toInt()
        //val w = key_0.width
        //val h = key_0.height
        //val color = Color.WHITE
        val sd = ShapeDrawable()

        val paint = Paint()
        val paint2 = Paint()

        // 黒の細い線
        paint.setStrokeWidth(5F)
        paint.setColor(Color.argb(255, 219, 49, 86))
        canvas.drawLine(210F, 25F, 210F, 615F, paint)

        paint.setStrokeWidth(5F)
        paint.setColor(Color.argb(255, 219, 49, 86))
        canvas.drawLine(850F, 25F, 850F, 615F, paint)
        sd.apply {
            //setBounds(x, y, x + w, y + h)
            //paint.color(color)
            draw(canvas)
        }
    }


    fun setKeyboardWith(controller:  keyboardController) {
        keyboard = controller.inflateKeyboard()
        //isLayoutForRightHand = isRight
    }

    /*fun changeKeyFace(index: Int, text: String) {
        changeKeyFace(index, { key ->
            key.label = text
        })
    }

    fun changeKeyFace(index: Int, drawable: Drawable) {
        changeKeyFace(index, { key ->
            key.label = null
            key.icon = drawable
        })
    }

    private fun changeKeyFace(index: Int, let: (Keyboard.Key) -> Unit) {
        //keyboard?.keys!![index]?.let(let) ?: return
        invalidateKey(index)
    }*/

}