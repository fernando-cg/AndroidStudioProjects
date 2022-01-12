package es.fesac.tictactoe.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.fesac.tictactoe.databinding.FragmentLoginBinding

class LoginFragment:BaseFragment() {
    private var binding:FragmentLoginBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //TODO observers
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        setUpViews()
        return binding?.root
    }

    private fun setUpViews() {
        //TODO Configure Views
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}