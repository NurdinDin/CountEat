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

    private val binding get() = _binding ?: throw IllegalStateException("Binding cannot be null after onCreateView")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"
    }
}