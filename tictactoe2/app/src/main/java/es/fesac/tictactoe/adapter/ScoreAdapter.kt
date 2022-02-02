package es.fesac.tictactoe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.fesac.tictactoe.databinding.ItemScoreBinding
import es.fesac.tictactoe.model.DatableItem
import es.fesac.tictactoe.model.ScoreBo

class ScoreAdapter(private var dataSet: List<ScoreBo>) : RecyclerView.Adapter<ScoreAdapter.ViewHolder>() {

    private var itemList = mutableListOf<DatableItem>()
    
    init {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreAdapter.ViewHolder {
        val binding = ItemScoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScoreAdapter.ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun updateDataSet(list: List<ScoreBo>) {
        dataSet = list
        notifyItemRangeInserted(0, itemCount)
    }

    inner class ViewHolder(private val binding: ItemScoreBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ScoreBo) {
            binding.itemScoreLabelScore.text = item.score
        }
    }
}