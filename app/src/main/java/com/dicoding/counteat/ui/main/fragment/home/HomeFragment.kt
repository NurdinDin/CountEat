package com.dicoding.counteat.ui.main.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dicoding.counteat.databinding.FragmentHomeBinding
import com.dicoding.counteat.ui.factory.ViewModelFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(this)
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding ?: throw IllegalStateException("Binding cannot be null after onCreateView")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


       /* val username = EXTRA_USERNAME
        viewModel.findUser(username, bmr)
        viewModel.bmrUser.observe(viewLifecycleOwner) { item ->
            item?.let {
                binding.tvCal = it.bmr
            }
        }*/

        return root
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"
    }
}