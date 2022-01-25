package es.fesac.tictactoe.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import es.fesac.tictactoe.adapter.ScoreAdapter
import es.fesac.tictactoe.databinding.FragmentHistoryBinding
import es.fesac.tictactoe.viewmodel.HistoryViewModel

class HistoryFragment:BaseFragment() {
    private var binding: FragmentHistoryBinding? = null
    private val viewmModel:HistoryViewModel by viewModels()
    private var adapter: ScoreAdapter?= null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentHistoryBinding.inflate(inflater)
        setUpViews()
        return binding?.root
    }

    private fun setUpViews() {
        adapter= ScoreAdapter(dataSet = listOf("1-3","2-3","3-0","0-3"))
        binding?.histroryRecyclerItems?.adapter = adapter
    }
}