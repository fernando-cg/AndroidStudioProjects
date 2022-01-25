package es.fesac.tictactoe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.fesac.tictactoe.databinding.ItemScoreBinding

class ScoreAdapter(val dataSet: List<String>): RecyclerView.Adapter<ScoreAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemScoreBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount(): Int {
       return dataSet.size
    }

    inner class ViewHolder(val binding: ItemScoreBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item:String){
            binding.itemScoreScoreLavel.text = item
        }
    }
}