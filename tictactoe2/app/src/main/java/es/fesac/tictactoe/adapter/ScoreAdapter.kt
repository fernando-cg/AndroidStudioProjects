package es.fesac.tictactoe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.fesac.tictactoe.databinding.ItemHeaderBinding
import es.fesac.tictactoe.databinding.ItemScoreBinding
import es.fesac.tictactoe.model.ScoreBo
import java.text.SimpleDateFormat
import java.util.*

class ScoreAdapter(private var dataSet: List<ScoreBo>,val clickAction: (String) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val headerId = 0
    private val itemId = 1

    private var scoreDateMap = mutableMapOf<String, MutableList<ScoreBo>>()

    private val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    override fun getItemViewType(position: Int): Int {
        val item = dataSet[position]
        return if (item.date == -1L) {
            headerId
        } else {
            itemId
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            headerId -> {
                val binding = ItemHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                HeaderViewHolder(binding)
            }
            else -> {
                val binding = ItemScoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                holder.bind(dataSet[position])
            }
            is HeaderViewHolder -> {
                holder.bind(dataSet[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun updateDataSet(list: List<ScoreBo>) {
        list.forEach { item ->
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = item.date
            val dateStr = simpleDateFormat.format(calendar.time)
            if (scoreDateMap.containsKey(dateStr)) {
                scoreDateMap[dateStr]?.add(item)
            } else {
                val mutableList = mutableListOf<ScoreBo>()
                mutableList.add(item)
                scoreDateMap[dateStr] = mutableList
            }
        }

        val allItems = mutableListOf<ScoreBo>()
        scoreDateMap.entries.forEach { entry ->
            allItems.add(ScoreBo(score = entry.key, date = -1))
            allItems.addAll(entry.value)
        }

        dataSet = allItems
        notifyItemRangeInserted(0, itemCount)
    }

    inner class ViewHolder(private val binding: ItemScoreBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ScoreBo) {
            binding.itemScoreLabelScore.text = item.score
            binding.root.setOnClickListener{
                clickAction(item.score)
            }
        }
    }

    inner class HeaderViewHolder(private val binding: ItemHeaderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ScoreBo) {
            binding.itemHeaderLabelTitle.text = item.score
        }
    }
}