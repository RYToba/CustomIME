package com.example.toba.customime

import android.graphics.drawable.BitmapDrawable


class IndicatorFactory (var backgrounds: BackgroundDrawables){

    class BackgroundDrawables(
            val default: BitmapDrawable,
            val onTap:   BitmapDrawable,
            val onLeft:  BitmapDrawable,
            val onRight: BitmapDrawable,
            val onUp:    BitmapDrawable,
            val onDown:  BitmapDrawable,
            val disable: BitmapDrawable)

    var left   = 0
    var right  = 0
    var top    = 0
    var bottom = 0

    var enable = true

    var isDuringInput = false

    var direction = Flick.Direction.NONE

    var currentDistance = 0

    var maxDistance = 0

    fun makeIndicator(): BitmapDrawable {
        return with(backgrounds) {
            if (!enable) {
                disable
            } else if (!isDuringInput) {
                default
            } else if (maxDistance <= 0) {
                onTap
            } else {
                when (direction) {
                    Flick.Direction.NONE  -> onTap
                    Flick.Direction.LEFT  -> onLeft
                    Flick.Direction.RIGHT -> onRight
                    Flick.Direction.UP    -> onUp
                    Flick.Direction.DOWN  -> onDown
                }
            }
        }.apply {
            setBounds(left, top, right, bottom)
            alpha = when {
                !enable -> 255
                !isDuringInput -> 255
                direction == Flick.Direction.NONE -> 255
                maxDistance <= 0 -> 255
                currentDistance >= maxDistance -> 255
                else -> 255 * currentDistance / maxDistance
            }
        }
    }

}