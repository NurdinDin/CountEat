package com.dicoding.counteat.ui.insert_tb_bb

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.counteat.data.state.State
import com.dicoding.counteat.databinding.ActivityTbBbBinding
import com.dicoding.counteat.ui.factory.ViewModelFactory
import com.dicoding.counteat.ui.main.MainActivity

class TbBbActivity : AppCompatActivity() {

    lateinit var binding: ActivityTbBbBinding

    private val viewModel: TBbBbViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTbBbBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getRegisterSession().observe(this) {}

        setupAction()
    }

    private fun setupAction() {
        binding.btnNext.setOnClickListener {
            val tb = binding.edtTb.text.toString()
            val bb = binding.edtBb.text.toString()
            val age = binding.edtUmur.text.toString()
            viewModel.username.observe(this) { username ->
                viewModel.tbUser(username, tb, bb, age).observe(this) {
                    if (it != null) {
                        when(it) {
                            is State.Success -> {
                                AlertDialog.Builder(this).apply {
                                    setTitle("Berhasil!")
                                    setMessage("Silakan lanjut")
                                    setPositiveButton("Lanjut") { _, _ ->
                                        val intent = Intent(this@TbBbActivity, MainActivity::class.java)
                                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                        startActivity(intent)
                                        finish()
                                    }
                                    create()
                                    show()
                                }
                                showLoading(false)
                            }
                            is State.Error -> {
                                AlertDialog.Builder(this).apply {
                                    setTitle("Gagal!")
                                    setMessage("Silahkan input ulang!")
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
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}