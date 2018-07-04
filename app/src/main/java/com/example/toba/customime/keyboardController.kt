package com.example.toba.customime

import android.content.Context
import android.inputmethodservice.Keyboard


class keyboardController(private val context: Context) {
    private var keyboard : Keyboard? = null

    var isRight = true
        private set
    var isPortrait = true
        private set
    var heightLevel = 3
        private set

    fun inflateKeyboard(): Keyboard {
        //this.isRight = isRight
        //this.isPortrait = useFooter
        //this.heightLevel = heightLevel

        val keyboard1 = Keyboard(this.context, R.layout.keyboard)
        this.keyboard = keyboard1

        return keyboard1
    }
}