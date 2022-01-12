package es.fesac.tictactoe.view


import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import es.fesac.tictactoe.R
import es.fesac.tictactoe.databinding.CustomViewBoardBiasBinding
import es.fesac.tictactoe.model.BoardItem

const val MAX_ITEMS = 9

class BoardView(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    companion object {
        const val MAX_ITEMS = 9
    }

    interface BoardClickItemListener {
        fun clickOnItem(imageView: ImageView, position: Int)
    }

    var listener: BoardClickItemListener? = null
    private var binding: CustomViewBoardBiasBinding? = null

    init {
        binding = CustomViewBoardBiasBinding.inflate(LayoutInflater.from(context), this, true)
        setUpClickItems()
    }

    private fun getImageFromPosition(position: Int): ImageView? {
        return when (position) {
            0 -> binding?.customViewImg0
            1 -> binding?.customViewImg1
            2 -> binding?.customViewImg2
            3 -> binding?.customViewImg3
            4 -> binding?.customViewImg4
            5 -> binding?.customViewImg5
            6 -> binding?.customViewImg6
            7 -> binding?.customViewImg7
            8 -> binding?.customViewImg8
            else -> null
        }
    }

    private fun setUpClickItems() {
        for (item in 0 until MAX_ITEMS) {
            getImageFromPosition(item)?.setOnClickListener {
                if (isEnabled) {
                    listener?.clickOnItem(it as ImageView, item)
                }
            }
        }
    }

    fun updateState(listItems: List<BoardItem>) {
        if (listItems.size != MAX_ITEMS) {
            throw IllegalArgumentException("List item size must be equal MAX_ITEMS")
        }
        listItems.forEachIndexed { index, boardItem ->
            boardItem.resId?.let {
                getImageFromPosition(index)?.setImageResource(it)
            } ?: kotlin.run {
                getImageFromPosition(index)?.setImageDrawable(null)
            }
        }
    }

    fun clear() {
        for (item in 0 until MAX_ITEMS) {
            getImageFromPosition(item)?.setImageDrawable(null)
            getImageFromPosition(item)?.setColorFilter(ResourcesCompat.getColor(resources, R.color.black, null))
        }
    }

    fun updateWinCombination(winCombination: List<Int>) {
        winCombination.forEach { position ->
            getImageFromPosition(position)?.setColorFilter(ResourcesCompat.getColor(resources, R.color.green, null))
        }
    }
}