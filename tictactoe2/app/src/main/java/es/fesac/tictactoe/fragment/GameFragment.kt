package es.fesac.tictactoe.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import es.fesac.tictactoe.R
import es.fesac.tictactoe.databinding.FragmentGameBinding
import es.fesac.tictactoe.extension.showToast
import es.fesac.tictactoe.model.BoardItem
import es.fesac.tictactoe.view.BoardView
import es.fesac.tictactoe.viewmodel.GameViewModel

class GameFragment : BaseFragment() {

    val args: GameFragmentArgs by navArgs()
    private lateinit var binding: FragmentGameBinding

    private val viewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeBoardState()
        observeChanceState()
        observeScoreState()
        observeWinnerState()
        observeWinCombinationState()
        viewModel.initState()
    }

    private fun observeWinCombinationState() {
        viewModel.getWinCombinationLiveData().removeObservers(this)
        viewModel.getWinCombinationLiveData().observe(this, { winCombination ->
            binding.gameViewBoard.updateWinCombination(winCombination)
            binding.gameViewBoard.isEnabled = false
        })
    }

    private fun observeWinnerState() {
        viewModel.getWinnerLiveData().removeObservers(this)
        viewModel.getWinnerLiveData().observe(this, {
            binding.gameViewBoard.clear()
            binding.gameViewBoard.isEnabled = true
        })
    }

    private fun observeScoreState() {
        viewModel.getScoreLiveData().removeObservers(this)
        viewModel.getScoreLiveData().observe(this, { score ->
            setScore(score)
        })
    }

    private fun setScore(score: String) {
        binding.gameLabelScore.text = score
    }

    private fun observeChanceState() {
        viewModel.getChanceLiveData().removeObservers(this)
        viewModel.getChanceLiveData().observe(this, { chance ->
           setChance(chance)
        })
    }

    private fun setChance(chance: BoardItem) {
        if (chance == BoardItem.CROSS) {
            binding.gameViewCrossChance.visibility = View.VISIBLE
            binding.gameViewCircleChance.visibility = View.GONE
        } else {
            binding.gameViewCrossChance.visibility = View.GONE
            binding.gameViewCircleChance.visibility = View.VISIBLE
        }
    }

    private fun observeBoardState() {
        viewModel.getBoardStateLiveData().removeObservers(this)
        viewModel.getBoardStateLiveData().observe(this, {
            binding.gameViewBoard.updateState(it)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameBinding.inflate(inflater)
        setUpViews()
        return binding.root
    }

    private fun setUpViews() {
        binding.gameViewBoard.listener = object : BoardView.BoardClickItemListener {
            override fun clickOnItem(imageView: ImageView, position: Int) {
                viewModel.clickOnItem(position)
            }
        }
    }

    /* Lambda sample
    fun subs(number: Int, numberToSubs: Int, action: () -> Unit = {}): Int {
        val subs = number - numberToSubs
        if (subs < 0) {
            action()
        }
        return subs
    }*/
}