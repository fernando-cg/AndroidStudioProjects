package es.fesac.practica.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import es.fesac.practica.databinding.CustomViewLoadingBinding
import es.fesac.practica.ui.extension.invisible
import es.fesac.practica.ui.extension.invisibleWithScaleAnimation
import es.fesac.practica.ui.extension.showWithScaleAnimation

class LoadingView(context: Context, attributeSet: AttributeSet) :
    ConstraintLayout(context, attributeSet) {

    var binding: CustomViewLoadingBinding? = null

    init {
        binding = CustomViewLoadingBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun start() {
        stop()
        showNumbers()
    }

    fun stop() {
        binding?.card2?.clearAnimation()
        binding?.card0?.clearAnimation()
        binding?.card4?.clearAnimation()
        binding?.card8?.clearAnimation()
        binding?.card2?.invisible()
        binding?.card0?.invisible()
        binding?.card4?.invisible()
        binding?.card8?.invisible()
    }

    private fun invisibleNumbers() {
        binding?.card2?.invisibleWithScaleAnimation {
            binding?.card0?.invisibleWithScaleAnimation {
                binding?.card4?.invisibleWithScaleAnimation {
                    binding?.card8?.invisibleWithScaleAnimation {
                        showNumbers()
                    }
                }
            }
        }
    }

    private fun showNumbers() {
        binding?.card2?.showWithScaleAnimation {
            binding?.card0?.showWithScaleAnimation {
                binding?.card4?.showWithScaleAnimation {
                    binding?.card8?.showWithScaleAnimation {
                        invisibleNumbers()
                    }
                }
            }
        }
    }

    override fun onDetachedFromWindow() {
        binding = null
        super.onDetachedFromWindow()
    }
}