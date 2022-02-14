package es.fesac.practica.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import es.fesac.practica.databinding.ItemUserRankingBinding
import es.fesac.practica.ui.model.UserRankingVo

class RankingAdapter(private var dataSet: List<UserRankingVo>) : RecyclerView.Adapter<RankingAdapter.UserRankingViewHolder>() {

    fun updateDataSet(newDataSet: List<UserRankingVo>) {
        dataSet = newDataSet
        notifyItemRangeInserted(0, itemCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingAdapter.UserRankingViewHolder {
        val binding = ItemUserRankingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserRankingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RankingAdapter.UserRankingViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size

    inner class UserRankingViewHolder(private val binding: ItemUserRankingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UserRankingVo) {
            Glide.with(binding.root.context).load(item.userImgUrl).into(binding.itemUserRankingImgAvatar)
            binding.itemUserRankingLabelUser.text = item.userName
            binding.itemUserRankingLabelScore.text = item.score.toString()
        }
    }
}