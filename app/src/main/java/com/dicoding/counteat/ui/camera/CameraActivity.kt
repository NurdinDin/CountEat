package com.dicoding.counteat.ui.camera

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.counteat.databinding.ActivityCameraBinding

class CameraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


}