package es.fesac.tictactoe.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.divider.MaterialDividerItemDecoration
import es.fesac.tictactoe.adapter.ScoreAdapter
import es.fesac.tictactoe.databinding.FragmentHistoryBinding
import es.fesac.tictactoe.extension.showToast
import es.fesac.tictactoe.model.ScoreBo
import es.fesac.tictactoe.viewmodel.HistoryViewModel

class HistoryFragment : BaseFragment() {
    private var binding: FragmentHistoryBinding? = null
    private val viewModel: HistoryViewModel by viewModels()
    private var adapter: ScoreAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLoadingObserver()
        setErrorObserver()
        setScoreListObserver()
    }

    private fun setScoreListObserver() {
        viewModel.getScoreListLiveData().removeObservers(this)
        viewModel.getScoreListLiveData().observe(this, { list ->
            updateScoreList(list)
        })
    }

    private fun updateScoreList(list: List<ScoreBo>) {
        adapter?.updateDataSet(list)
    }

    private fun setErrorObserver() {
        viewModel.getErrorLiveData().removeObservers(this)
        viewModel.getErrorLiveData().observe(this, { error ->
            error?.let {
                showToast(error)
            }
        })
    }

    private fun setLoadingObserver() {
        viewModel.getLoadingLiveData().removeObservers(this)
        viewModel.getLoadingLiveData().observe(this, { loading ->
            showLoading(loading)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater)
        setUpViews()
        viewModel.loadScoreList()
        return binding?.root
    }

    private fun setUpViews() {
        val list = mutableListOf<ScoreBo>()
        adapter = ScoreAdapter(dataSet = list)
        val divider = MaterialDividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        binding?.historyRecyclerItems?.addItemDecoration(divider)
        binding?.historyRecyclerItems?.adapter = adapter
    }
}