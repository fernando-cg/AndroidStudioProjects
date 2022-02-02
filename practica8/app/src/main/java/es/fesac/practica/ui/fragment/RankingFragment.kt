package es.fesac.practica.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import es.fesac.practica.databinding.FragmentRankingBinding
import es.fesac.practica.ui.adapter.RankingAdapter
import es.fesac.practica.ui.extension.showToast
import es.fesac.practica.ui.model.UserRankingVo
import es.fesac.practica.ui.viewmodel.RankingViewModel

class RankingFragment : BaseFragment() {

    private lateinit var binding: FragmentRankingBinding
    private val viewModel: RankingViewModel by viewModels()
    private var adapter: RankingAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeLoadingState()
        observeErrorState()
        observeRankingListState()
    }

    private fun observeErrorState() {
        viewModel.getErrorLiveData().removeObservers(this)
        viewModel.getErrorLiveData().observe(this, { messageToShow ->
            messageToShow?.let {
                showToast(messageToShow)
            }
        })
    }

    private fun observeLoadingState() {
        viewModel.getLoadingLiveData().removeObservers(this)
        viewModel.getLoadingLiveData().observe(this, { loading ->
            showLoading(loading)
        })
    }

    private fun observeRankingListState() {
        viewModel.getRankingLiveData().removeObservers(this)
        viewModel.getRankingLiveData().observe(this, { ranking ->
            updateRanking(ranking)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRankingBinding.inflate(inflater, container, false)
        setUpViews()
        viewModel.loadRanking()
        return binding.root
    }

    private fun setUpViews() {
        setUpRecycler()
    }

    private fun setUpRecycler() {
        // TODO 8.4: Configurar el adapter y vincularlo al RecyclerView
        val list = mutableListOf<UserRankingVo>()
        adapter = RankingAdapter(dataSet = list)
        binding?.rankingRecyclerItems?.adapter = adapter
    }

    private fun updateRanking(ranking: List<UserRankingVo>) {
        // TODO 8.4: Actualizar listado del adapter
        adapter?.updateDataSet(ranking)
    }
}