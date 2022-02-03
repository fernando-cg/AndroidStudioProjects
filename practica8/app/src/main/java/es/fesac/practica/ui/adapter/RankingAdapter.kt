package es.fesac.practica.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import es.fesac.practica.databinding.ItemUserRankingBinding

import es.fesac.practica.ui.model.UserRankingVo

/**
 * TODO 8.5: Debe manejar el listado de UserRankingVo y pintar los datos correctamente.
 * La vista de cada item debe quedar tal como se ve en la imagen. Para la carga de la imagen, se debe
 * usar la librería de Glide (no es necesario incluir la dependencia).
 */
class RankingAdapter(private var dataSet:List<UserRankingVo>) : RecyclerView.Adapter<RankingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingAdapter.ViewHolder {
        val binding = ItemUserRankingBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RankingAdapter.ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun updateDataSet(list: List<UserRankingVo>) {
        dataSet = list
        notifyItemRangeInserted(0, itemCount)
    }

    inner class ViewHolder(private val binding: ItemUserRankingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UserRankingVo) {
            binding.itemScoreLabelUser.text = item.userName
            binding.itemScoreLabelScore.text = item.score.toString()
            setUpLogo(item)
        }

        private fun setUpLogo(UserRanking: UserRankingVo) {
            Glide
                .with(binding.root.context)
                .load(UserRanking.userImgUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .fitCenter()
                .into(binding.image)
        }
    }
}