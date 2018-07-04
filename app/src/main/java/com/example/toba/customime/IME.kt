package com.example.toba.customime

import android.accessibilityservice.AccessibilityService
import android.annotation.SuppressLint
import android.graphics.drawable.BitmapDrawable
import android.inputmethodservice.InputMethodService
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo

class IME : InputMethodService() , KeyboardView.OnKeyboardActionListener {

    private lateinit var keyboardView: CustomKeyboardView
    //private lateinit var keyboard: Keyboard

    private val keyboardController by lazy {keyboardController(this)}

    var tapX0 = 0
    var tapY0 = 0

    // current action information
    private var tapX = -1
    private var tapY = -1

    private lateinit var indicatorFactory: IndicatorFactory
    private var canInput = true
    private var lastAction = MotionEvent.ACTION_UP
    private var flick = Flick.NONE
    private val keymap = KeymapController()
    private var onPressCode = KeyIndex.NOTHING
    //private lateinit var arrowKey: ArrowKey

    private fun supplyIndicatorBackground(): IndicatorFactory.BackgroundDrawables{

            var backgrounds = IndicatorFactory.BackgroundDrawables(
                    bitmapResourceOf(R.drawable.s_toumeitoumei),
                    bitmapResourceOf(R.drawable.s_hover_aa),
                    bitmapResourceOf(R.drawable.s_ii),
                    bitmapResourceOf(R.drawable.s_toumeitoumei),
                    bitmapResourceOf(R.drawable.s_toumeitoumei),
                    bitmapResourceOf(R.drawable.s_toumeitoumei),
                    bitmapResourceOf(R.drawable.s_toumeitoumei))

        return backgrounds
    }

    private fun bitmapResourceOf(@DrawableRes id: Int): BitmapDrawable =
            ResourcesCompat.getDrawable(resources, id, null) as BitmapDrawable


    //初回だけ呼ばれる
    override fun onCreate() {
        super.onCreate()

    }

    //初回だけ呼ばれる
    @SuppressLint("InflateParams")
    override fun onCreateInputView(): View {
        super.onCreateInputView()

        keyboardView = layoutInflater.inflate(R.layout.keyboard_view, null) as CustomKeyboardView
        return keyboardView.apply {
            setKeyboardWith(keyboardController)
            isPreviewEnabled = false

            setOnCloseListener {
                lastAction = MotionEvent.ACTION_UP
                tapX = -1
                tapY = -1
                onPressCode = KeyIndex.NOTHING
                canInput = true
                flick = Flick.NONE
                indicateFlickState()
            }
            setOnKeyboardActionListener(this@IME)
            setOnTouchListener Listner@{ _,motionEvent ->
                val x = motionEvent.x.toInt()
                val y = motionEvent.y.toInt()
                val action = motionEvent.action and MotionEvent.ACTION_MASK
                lastAction = action

                when(motionEvent.action and MotionEvent.ACTION_MASK){
                    MotionEvent.ACTION_DOWN ->{
                        tapX0 = 0
                        tapY0 = 0
                        Log.d("LocalLog", "タッチしました")
                        tapX = x
                        tapY = y
                        keyboard.keys.find {
                            it.isInside(x, y) && KeyIndex.isValid(it.codes[0])
                        }?.also {
                            val key = keymap.getKey(it.codes[0], Flick.NONE)
                        }
                        indicateFlickState()
                        return@Listner false
                    }
                    MotionEvent.ACTION_MOVE ->{
                        //flick = flickFactory.makeWith(tapX, tapY, x, y)
                        tapX0 = tapX - x
                        tapY0 = tapY - y
                        Log.d("LocalLog", "フリック(左へ${tapX - x}px、上へ${tapY - y}px)")
                        //indicateFlickState()
                        return@Listner true
                    }
                    MotionEvent.ACTION_UP ->{
                        Log.d("LocalLog", "タッチ終わりました")
                        indicateFlickState()
                        return@Listner false
                    }
                    else -> return@Listner true
                }

            }
        }
        //keyboard = Keyboard(this, R.layout.keyboard)
        //keyboardView.setKeyboard(keyboard)
        //keyboardView.setOnKeyboardActionListener(this)

        //keyboardView.setPreviewEnabled(false)
        //return keyboardView
    }


    //キーボードが表示されるたびに呼ばれるメソッド
    override fun onStartInputView(editorInfo: EditorInfo, restarting: Boolean) {
        //なんらかの処理
        super.onStartInputView(editorInfo, restarting)
        indicatorFactory = IndicatorFactory(supplyIndicatorBackground())
        indicateFlickState()
    }

    override fun onKey(primaryCode: Int, keyCodes: IntArray?) {
        val ic = currentInputConnection
        when (primaryCode) {
            KeyEvent.KEYCODE_1 -> {
                if(tapX0 > 70 && tapY0 < 70 && tapY0 > -70){
                    ic.commitText("い", 1)
                }
                else if(tapX0 < -70 && tapY0 < 70 && tapY0 > -70){
                    ic.commitText("え", 1)
                }
                else if(tapY0 > 70 && tapX0 < 70 && tapX0 > -70){
                    ic.commitText("う", 1)
                }
                else if(tapY0 < -70 && tapX0 < 70 && tapX0 > -70){
                    ic.commitText("お", 1)
                }
                else ic.commitText("あ", 1)
            }
            KeyEvent.KEYCODE_2 -> {
                if(tapX0 > 70 && tapY0 < 70 && tapY0 > -70){
                    ic.commitText("き", 1)
                }
                else if(tapX0 < -70 && tapY0 < 70 && tapY0 > -70){
                    ic.commitText("け", 1)
                }
                else if(tapY0 > 70 && tapX0 < 70 && tapX0 > -70){
                    ic.commitText("く", 1)
                }
                else if(tapY0 < -70 && tapX0 < 70 && tapX0 > -70){
                    ic.commitText("こ", 1)
                }
                else ic.commitText("か", 1)
            }
            KeyEvent.KEYCODE_3 -> {
                if(tapX0 > 70 && tapY0 < 70 && tapY0 > -70){
                    ic.commitText("し", 1)
                }
                else if(tapX0 < -70 && tapY0 < 70 && tapY0 > -70){
                    ic.commitText("せ", 1)
                }
                else if(tapY0 > 70 && tapX0 < 70 && tapX0 > -70){
                    ic.commitText("す", 1)
                }
                else if(tapY0 < -70 && tapX0 < 70 && tapX0 > -70){
                    ic.commitText("そ", 1)
                }
                else ic.commitText("さ", 1)
            }
            KeyEvent.KEYCODE_4 -> {
                if(tapX0 > 70 && tapY0 < 70 && tapY0 > -70){
                    ic.commitText("ち", 1)
                }
                else if(tapX0 < -70 && tapY0 < 70 && tapY0 > -70){
                    ic.commitText("て", 1)
                }
                else if(tapY0 > 70 && tapX0 < 70 && tapX0 > -70){
                    ic.commitText("つ", 1)
                }
                else if(tapY0 < -70 && tapX0 < 70 && tapX0 > -70){
                    ic.commitText("と", 1)
                }
                else ic.commitText("た", 1)
            }
            KeyEvent.KEYCODE_5 -> {
                if(tapX0 > 70 && tapY0 < 70 && tapY0 > -70){
                    ic.commitText("に", 1)
                }
                else if(tapX0 < -70 && tapY0 < 70 && tapY0 > -70){
                    ic.commitText("ね", 1)
                }
                else if(tapY0 > 70 && tapX0 < 70 && tapX0 > -70){
                    ic.commitText("ぬ", 1)
                }
                else if(tapY0 < -70 && tapX0 < 70 && tapX0 > -70){
                    ic.commitText("の", 1)
                }
                else ic.commitText("な", 1)
            }
            KeyEvent.KEYCODE_6 -> {
                if(tapX0 > 70 && tapY0 < 70 && tapY0 > -70){
                    ic.commitText("ひ", 1)
                }
                else if(tapX0 < -70 && tapY0 < 70 && tapY0 > -70){
                    ic.commitText("へ", 1)
                }
                else if(tapY0 > 70 && tapX0 < 70 && tapX0 > -70){
                    ic.commitText("ふ", 1)
                }
                else if(tapY0 < -70 && tapX0 < 70 && tapX0 > -70){
                    ic.commitText("ほ", 1)
                }
                else ic.commitText("は", 1)
            }
            KeyEvent.KEYCODE_7 -> {
                if(tapX0 > 70 && tapY0 < 70 && tapY0 > -70){
                    ic.commitText("み", 1)
                }
                else if(tapX0 < -70 && tapY0 < 70 && tapY0 > -70){
                    ic.commitText("め", 1)
                }
                else if(tapY0 > 70 && tapX0 < 70 && tapX0 > -70){
                    ic.commitText("む", 1)
                }
                else if(tapY0 < -70 && tapX0 < 70 && tapX0 > -70){
                    ic.commitText("も", 1)
                }
                else ic.commitText("ま", 1)
            }
            KeyEvent.KEYCODE_8 -> {
                if(tapY0 > 70 && tapX0 < 70 && tapX0 > -70){
                    ic.commitText("ゆ", 1)
                }
                else if(tapY0 < -70 && tapX0 < 70 && tapX0 > -70){
                    ic.commitText("よ", 1)
                }
                else ic.commitText("や", 1)
            }
            KeyEvent.KEYCODE_9 -> {
                if(tapX0 > 70 && tapY0 < 70 && tapY0 > -70){
                    ic.commitText("り", 1)
                }
                else if(tapX0 < -70 && tapY0 < 70 && tapY0 > -70){
                    ic.commitText("れ", 1)
                }
                else if(tapY0 > 70 && tapX0 < 70 && tapX0 > -70){
                    ic.commitText("る", 1)
                }
                else if(tapY0 < -70 && tapX0 < 70 && tapX0 > -70){
                    ic.commitText("ろ", 1)
                }
                else ic.commitText("ら", 1)
            }
            KeyEvent.KEYCODE_0 -> {
                if(tapX0 > 70 && tapY0 < 70 && tapY0 > -70){
                    ic.commitText("を", 1)
                }
                else if(tapX0 < -70 && tapY0 < 70 && tapY0 > -70){
                    ic.commitText("ー", 1)
                }
                else if(tapY0 > 70 && tapX0 < 70 && tapX0 > -70){
                    ic.commitText("ん", 1)
                }
                else if(tapY0 < -70 && tapX0 < 70 && tapX0 > -70){
                    ic.commitText("～", 1)
                }
                else ic.commitText("わ", 1)
            }
            KeyEvent.KEYCODE_DPAD_LEFT -> ic.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_LEFT))
            KeyEvent.KEYCODE_DPAD_RIGHT -> ic.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_RIGHT))
            KeyEvent.KEYCODE_DEL -> ic.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL))
            KeyEvent.KEYCODE_SPACE -> ic.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_SPACE))
            KeyEvent.KEYCODE_ENTER -> ic.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER))
            else -> {
            }
        }
    }



    //キーボードが閉じる時に呼ばれるメソッド
    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onPress(primaryCode: Int) {
        onPressCode = primaryCode
        canInput = KeyIndex.isValid(primaryCode)
        indicateFlickState()
    }

    private fun indicateFlickState() {
        //val key = keyboardView.keyboard.keys.first{ it.codes[0] == KeyIndex.INDICATOR } ?: return

        val key : Keyboard.Key = keyboardView.keyboard.keys[2]

        indicatorFactory.also {

            it.enable = canInput
            it.isDuringInput = lastAction != MotionEvent.ACTION_UP
            it.left = key.x
            it.right = key.x + key.width
            it.top = key.y
            it.bottom = key.y + key.height
            it.direction = flick.direction
            it.currentDistance = flick.distance
            it.maxDistance = keymap.getMaxDistance(onPressCode, flick.direction)
            Log.d("x"+key+" "+it.right+" "+it.top+" "+" "+it.bottom, "aaaa")
            keyboardView.apply {
                indicator = indicatorFactory.makeIndicator()
            }
        }
    }

    override fun onRelease(primaryCode: Int) {
        //arrowKey.stopInput()
        tapX = -1
        tapY = -1
        onPressCode = KeyIndex.NOTHING
        canInput = true
        flick = Flick.NONE
        indicateFlickState()
    }

    override fun onText(text: CharSequence) {}

    override fun swipeLeft() {
        //flick = Flick.LEFT
        //indicateFlickState()
    }

    override fun swipeRight() {}

    override fun swipeDown() {}

    override fun swipeUp() {}
}