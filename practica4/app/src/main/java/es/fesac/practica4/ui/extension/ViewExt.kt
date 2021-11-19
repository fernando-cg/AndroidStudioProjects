package es.formacion.game2048.extension

import android.app.Activity
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.ScaleAnimation
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService

const val MIN_DURATION = 350L

fun View?.setVisible(condition: Boolean, onVisible: () -> Unit = {}) {
    this?.visibility = if (condition) {
        onVisible()
        View.VISIBLE
    } else {
        View.GONE
    }
}

fun View?.setInvisible(condition: Boolean, onVisible: () -> Unit = {}) {
    this?.visibility = if (condition) {
        onVisible()
        View.INVISIBLE
    } else {
        View.VISIBLE
    }
}

fun View?.show() {
    this?.visibility = View.VISIBLE
}

fun View?.hide() {
    this?.visibility = View.GONE
}

fun View?.invisible() {
    this?.visibility = View.INVISIBLE
}

fun View.showWithScaleAnimation(onEnd: () -> Unit = {}){
    val scaleUp = ScaleAnimation(0F, 1F, 0F, 1F, Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5F)
    scaleUp.apply {
        reset()
        fillAfter = true
        duration = 250L
        setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationStart(animation: Animation?) {
                this@showWithScaleAnimation.show()
            }

            override fun onAnimationEnd(animation: Animation?) {
                onEnd()
            }

            override fun onAnimationRepeat(animation: Animation?) {

            }

        })
    }
    this.clearAnimation()
    this.startAnimation(scaleUp)
}

fun View.invisibleWithScaleAnimation(onEnd: () -> Unit = {}){
    val scaleDown = ScaleAnimation(1F, 0F, 1F, 0F, Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5F)
    scaleDown.apply {
        reset()
        fillAfter = true
        duration = 250L
        setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                this@invisibleWithScaleAnimation.invisible()
                onEnd()
            }

            override fun onAnimationRepeat(animation: Animation?) {

            }

        })
    }
    this.clearAnimation()
    this.startAnimation(scaleDown)
}

fun View.hideSoftKeyboard() {
    val inputMethodManager = this.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(this.windowToken, 0)
}

fun View.fadeIn() {
    val fadeIn = AnimationUtils.loadAnimation(context,android.R.anim.fade_in)
    fadeIn.duration = MIN_DURATION
    fadeIn.startOffset = 300L
    fadeIn.setAnimationListener(object : Animation.AnimationListener{
        override fun onAnimationStart(p0: Animation?) {

            show()
        }

        override fun onAnimationEnd(p0: Animation?) {
            //NO-OP
        }

        override fun onAnimationRepeat(p0: Animation?) {
            //NO-OP
        }
    })

    startAnimation(fadeIn)
}

fun View.fadeOut() {
    val fadeOut = AnimationUtils.loadAnimation(context,android.R.anim.fade_out)
    fadeOut.duration = MIN_DURATION
    fadeOut.setAnimationListener(object : Animation.AnimationListener{
        override fun onAnimationStart(p0: Animation?) {
            //NO-OP
        }

        override fun onAnimationEnd(p0: Animation?) {
            hide()
        }

        override fun onAnimationRepeat(p0: Animation?) {
            //NO-OP
        }
    })

    startAnimation(fadeOut)
}