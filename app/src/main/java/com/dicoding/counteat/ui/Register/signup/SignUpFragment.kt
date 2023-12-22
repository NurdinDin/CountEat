package com.dicoding.counteat.ui.Register.signup

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dicoding.counteat.data.pref.RegisterModel
import com.dicoding.counteat.data.state.State
import com.dicoding.counteat.databinding.FragmentSignUpBinding
import com.dicoding.counteat.ui.Register.TabLayoutActivity
import com.dicoding.counteat.ui.factory.ViewModelFactory


class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SignUpViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupAction()

        return root
    }

    private fun setupAction() {
        binding.btnSignUp.setOnClickListener {
            val name = binding.edtName.text.toString()
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            val gender = binding.edtGender.text.toString()
            viewModel.signUp(name, email, password, gender).observe(viewLifecycleOwner) { signUp ->
                if (signUp != null) {
                    when(signUp) {
                        is State.Success -> {
                            val token = signUp.data.toString()
                            viewModel.saveSessionRegister(RegisterModel(name, email, password, gender, token))
                            AlertDialog.Builder(requireContext()).apply {
                                setTitle("Mantap!")
                                setMessage("Akun dengan $email sudah jadi nih. Yuk, login")
                                setPositiveButton("Lanjut") { _, _ ->
                                    val intent = Intent(requireContext(), TabLayoutActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                    startActivity(intent)

                                }
                                create()
                                show()
                            }
                            showLoading(false)
                        }
                        is State.Error -> {
                            AlertDialog.Builder(requireContext()).apply {
                                setTitle("Yah gagal!!")
                                setMessage("Silahkan buat ulang!")
                                setPositiveButton("Ulangi") { _, _ ->

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

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}