package com.dicoding.counteat.ui.main.fragment.profile

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dicoding.counteat.databinding.FragmentProfileBinding
import com.dicoding.counteat.ui.factory.ViewModelFactory

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private var currentImageUri: Uri? = null

    private val viewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.userProfile.setOnClickListener {
            launcherGalleryProfile.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }

        setupAction()

        return root
    }

    /*private fun startGalleryProfile() {
        launcherGalleryProfile.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }*/

    private val launcherGalleryProfile = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo picker", "No media selected")
        }
    }

    private fun showImage() {
        currentImageUri.let {
            Log.d("Image URI", "showImage: $it")
            binding.userProfile.setImageURI(it)
        }
    }

    private fun setupAction() {
        binding.btnLogout.setOnClickListener {
            viewModel.logout()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}