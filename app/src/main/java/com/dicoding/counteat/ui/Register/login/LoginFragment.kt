package com.dicoding.counteat.ui.Register.login

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dicoding.counteat.data.pref.UserModel
import com.dicoding.counteat.data.state.State
import com.dicoding.counteat.databinding.FragmentLoginBinding
import com.dicoding.counteat.ui.Register.TabLayoutActivity
import com.dicoding.counteat.ui.factory.ViewModelFactory
import com.dicoding.counteat.ui.insert_tb_bb.TbBbActivity


class LoginFragment : Fragment() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupAction()
        return root
    }

    private fun setupAction() {
        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            viewModel.login(email, password).observe(viewLifecycleOwner) { it
                if (it != null) {
                    when (it) {
                        is State.Success -> {
                            val token = it.data.toString()
                            viewModel.saveSession(UserModel(email, password, token))
                            AlertDialog.Builder(requireContext()).apply {
                                setTitle("Mantap")
                                setMessage("Anda berhasil login")
                                setPositiveButton("Lanjut") { _, _ ->
                                    val intent = Intent(requireContext(), TbBbActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                    startActivity(intent)

                                }
                                create()
                                show()
                            }
                            showLoading(false)
                        }

                        is State.Error -> {
                            AlertDialog.Builder(requireContext()).apply {
                                setTitle("Yahh gagal!!")
                                setMessage("Silahkan Coba lagi")
                                setPositiveButton("Coba lagi") { _, _ ->
                                    val intent = Intent(requireContext(), TabLayoutActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                }
                                create()
                                show()
                            }
                            showLoading(false)
                        }
                        is State.Loading -> {
                            showLoading(true)
                        }
                    }
                }
            }

        }
    }

    private fun isUserDataComplete(tb: String, bb: String, age: String) : Boolean {
        return tb.isNotEmpty() && bb.isNotEmpty() && age.isNotEmpty()
    }

    private fun showLoading(isLoading:Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}