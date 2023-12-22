package com.dicoding.counteat.ui.main.fragment.Scan

import android.app.AlertDialog
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.dicoding.counteat.databinding.FragmentScanBinding
import com.dicoding.counteat.ml.FoodClassifierModel
import com.dicoding.counteat.ui.camera.getImageUri
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.IOException

class ScanFragment : Fragment() {

    private var _binding: FragmentScanBinding? = null

    lateinit var btnGallery: Button
    lateinit var btnScan: Button
    lateinit var imgView: ImageView
    lateinit var bitmap: Bitmap

    private val binding get() = _binding!!
    private var currentImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentScanBinding.inflate(inflater, container, false)
        val root: View = binding.root

        var labels = requireActivity().application.assets.open("labels.txt").bufferedReader().readLines()
        var cal = requireActivity().application.assets.open("calorie.txt").bufferedReader().readLines()

        btnGallery = binding.galleryButton
        btnScan = binding.scanButton
        imgView = binding.previewImageView

        var imageProcessor = ImageProcessor.Builder()
            .add(ResizeOp(224, 224, ResizeOp.ResizeMethod.BILINEAR))
            .build()

        binding.galleryButton.setOnClickListener {
            startGallery()
        }
        binding.cameraButton.setOnClickListener {
            startCamera()
        }

        binding.scanButton.setOnClickListener {
            if (!::bitmap.isInitialized) {
                Log.e("ScanFragment", "Bitmap not initialized")
            }

            var tensorImage = TensorImage(DataType.FLOAT32)
            tensorImage.load(bitmap)

            tensorImage = imageProcessor.process(tensorImage)

            val model = FoodClassifierModel.newInstance(requireContext())

            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
            inputFeature0.loadBuffer(tensorImage.buffer)

            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer.floatArray

            var maxId = 0
            outputFeature0.forEachIndexed { index, fl ->
                if (outputFeature0[maxId] < fl) {
                    maxId = index
                }
            }

            val foodLabel = "Nama makanan : " + (labels.getOrNull(maxId) ?: "Tidak ada informasi makanan")
            val calorieInfo = "Informasi Kalori : " + (cal.getOrNull(maxId) ?: "Tidak ada informasi kalori makanan")

            val message = "$foodLabel\n$calorieInfo"

            AlertDialog.Builder(requireContext()).apply {
                setTitle("Makanan yang kamu scan:")
                setMessage(message)
                setPositiveButton("Ok") { _, _ ->

                }
                create()
                show()
            }

            model.close()
        }

        return root
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo picker", "No media selected")
        }
    }

    private fun startCamera() {
        currentImageUri = getImageUri(requireContext())
        launcherIntentCamera.launch(currentImageUri)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    private fun showImage() {
        currentImageUri.let { uri ->
            Log.d("Image URI", "showImage: $uri")
            try {
                bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
                binding.previewImageView.setImageURI(uri)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}