package com.example.toba.customime

class KeymapController {
    private val keymap = Keymap()

    init {
        //generateCharKeymap()
        //generateUnCharKeymap()
        //generateArrowKeymap(ArrowKey.Mode.DEFAULT)
        //generateFnKeymap()
    }

    /*fun updateArrowKeymap(mode: ArrowKey.Mode) {
        generateArrowKeymap(mode)
    }*/

    fun getKey(index: Int, flick: Flick): keyInfo.KeyInfo = keymap.getKey(index, flick)

    fun getMaxDistance(index: Int, direction: Flick.Direction): Int =
            keymap.getMaxDistance(index, direction)

    //fun getMaxDistance(): Int = keymap.getMaxDistance()
}