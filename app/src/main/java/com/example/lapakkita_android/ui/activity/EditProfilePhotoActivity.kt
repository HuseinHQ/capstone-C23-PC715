package com.example.lapakkita_android.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.example.lapakkita_android.R
import com.example.lapakkita_android.databinding.ActivityEditProfilePhotoBinding
import com.example.lapakkita_android.media.createCustomTempFile
import com.example.lapakkita_android.media.reduceFileImage
import com.example.lapakkita_android.media.uriToFile
import com.example.lapakkita_android.ui.components.BackButton
import com.example.lapakkita_android.ui.components.ButtonPrimary
import com.example.lapakkita_android.ui.components.CloseButton
import com.example.lapakkita_android.ui.components.ImageChooser
import java.io.File

class EditProfilePhotoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfilePhotoBinding
    private lateinit var profilePhoto: File
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfilePhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupComponent()
    }

    private fun setupComponent() {
        val editMain = intent.extras?.getBoolean(EDIT_MAIN) == true

        Glide.with(this)
            .load("https://i.pravatar.cc/300")
            .circleCrop()
            .placeholder(R.drawable.placeholder_image)
            .into(binding.previewImage)

        binding.closeBtn.setContent {
            CloseButton(
                onClick = {
                    if(editMain) {
                        startActivity(
                            Intent(
                                this,
                                MainActivity::class.java
                            )
                                .addFlags(
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK
                                            or Intent.FLAG_ACTIVITY_NEW_TASK
                                )
                        )
                    }
                    else{
                        finish()
                    }
                }
            )
        }
        binding.imageChooserBtn.setContent {
            ImageChooser(
                cameraClick = { startCamera() },
                galleryClick = { startGallery() }
            )
        }
        binding.imageUpdateBtn.setContent {
            ButtonPrimary(
                onClicked = {
                    if(editMain) {
                        startActivity(
                            Intent(
                                this,
                                MainActivity::class.java
                            )
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        or Intent.FLAG_ACTIVITY_NEW_TASK)
                        )
                    }
                    else {
                        finish()
                    }
                },
                text = resources.getString(R.string.change_profile_photo),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    private lateinit var currentPhotoPath: String
    private val cameraLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)

            myFile.let { file ->
                profilePhoto = reduceFileImage(file)
                Glide.with(this)
                    .load(BitmapFactory.decodeFile(file.path))
                    .circleCrop()
                    .placeholder(R.drawable.placeholder_image)
                    .into(binding.previewImage)
            }
            binding.imageUpdateBtn.visibility = View.VISIBLE
        }
    }
    private fun startCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            if(intent.resolveActivity(packageManager) != null) {
                createCustomTempFile(application).also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.example.lapakkita_android",
                        it
                    )
                    currentPhotoPath = it.absolutePath
                    intent.putExtra(
                        MediaStore.EXTRA_OUTPUT,
                        photoURI
                    )
                    cameraLauncher.launch(intent)
                }
            }
            else{
                Toast.makeText(this, resources.getString(R.string.not_granted_camera), Toast.LENGTH_SHORT).show()
            }
        }
        else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                100
            )
        }
    }


    private val galleryLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val selectedPhoto = it.data?.data as Uri
            selectedPhoto.let { uri ->
                val file = uriToFile(uri, this)
                profilePhoto = reduceFileImage(file)
                Glide.with(this)
                    .load(BitmapFactory.decodeFile(file.path))
                    .circleCrop()
                    .placeholder(R.drawable.placeholder_image)
                    .into(binding.previewImage)
            }
        }
    }
    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, resources.getString(R.string.take_image_from))
        galleryLauncher.launch(chooser)
    }

    companion object{
        const val EDIT_MAIN = "edit_main"
    }
}