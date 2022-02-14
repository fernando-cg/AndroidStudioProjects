package es.formacion.game2048.ui.view

import android.content.Context
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import java.lang.Exception
import kotlin.math.abs

open class OnSwipeTouchListener(context: Context): View.OnTouchListener {

    private val gestureDetector = GestureDetector(context, GestureListener())

    open fun onSwipeBottom() {
        // no-op
    }

    open fun onSwipeTop() {
        // no-op
    }

    open fun onSwipeRight() {
        // no-op
    }

    open fun onSwipeLeft() {
        // no-op
    }

    override fun onTouch(view: View?, event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    inner class GestureListener():GestureDetector.SimpleOnGestureListener(){
        private val swipeDistanceThreshold = 100
        private val swipeVelocityThreshold = 100

        override fun onDown(e: MotionEvent?): Boolean {
            return false
        }

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent?,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            var result = false

            try {
                if (e1 != null && e2 != null){
                    val difY = e2.y - e1.y
                    val difX = e2.x - e1.x
                    when {
                        abs(difX) > swipeDistanceThreshold && abs(velocityX) > swipeVelocityThreshold && abs(difX) > abs(difY) ->{
                            if (difX > 0){
                                onSwipeRight()
                            } else{
                                onSwipeLeft()
                            }
                            result = true
                        }

                        abs(difY) > swipeDistanceThreshold && abs(velocityY) > swipeVelocityThreshold ->{
                            if (difY > 0){
                                onSwipeBottom()
                            } else{
                                onSwipeTop()
                            }

                            result = true
                        }
                    }

                }
            } catch (e:Exception) {
                Log.e(OnSwipeTouchListener::class.java.simpleName, "Exception: ${e.message}")
            }

            return result
        }
    }
}