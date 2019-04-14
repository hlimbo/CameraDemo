package com.limbo.camerademo

import android.Manifest.permission.CAMERA
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import androidx.lifecycle.Observer
import com.ygoular.bitmapconverter.BitmapConverter
import java.util.*

class MainActivity : AppCompatActivity (), CallbackGlue {
    private val TAG ="MainActivity"
    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_TAKE_IMAGE = 123
    private var currentPhotoPath: String = ""
    private lateinit var photoViewModel: PhotoViewModel
    private var isObserverRegistered: Boolean = false
    private var mCurrentPhotoPath: String? = null
    private var PERMISSION_REQUEST_CODE = 10
    private val photoModelAdapter = PhotoModelAdapter(ArrayList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageButton.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                Log.d(TAG, "onClick function called")
//                var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
                // dispatchTakePictureIntent()

                if(checkPersmission()) dispatchTakePictureIntent() else requestPermission()
            }
        })

        photoViewModel = ViewModelProviders.of(this).get(PhotoViewModel::class.java)

        photoViewModel.testRoute().observe(this, Observer {
            Log.d(TAG, "it data: ${it.data}")
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, "onActivityResult called")

        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if(data != null && data.extras != null) {
                Log.d(TAG, "onActivityResult REQUEST_IMAGE_CAPTURE")
                val theData = data.extras!!.get("data") as Bitmap
                imageButton.setImageBitmap(theData)
               // val imgResource: ByteArray = bitmapConverter.convert(theData)

            } else {
                Log.d(TAG, "onActivityResult data(REQUEST: $data or ${data?.extras}")
            }
        } else
        if(requestCode == REQUEST_TAKE_IMAGE && resultCode == RESULT_OK) {

            val imageFile = File(mCurrentPhotoPath)
            photoViewModel.uploadPhoto(imageFile).observe(this, Observer {
                // display ui shit here
                // display ingredients thumbnail with ingredient name
                Log.d(TAG, "gooby pls")

            })
        }
    }

    private fun dispatchTakePictureIntent() {
        Log.d(TAG, ".dispatchPictureIntent callled")
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager).also {

                val file: File = createImageFile()
                val uri: Uri = FileProvider.getUriForFile(
                    this,
                    "com.example.android.fileprovider",
                    file
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
                startActivityForResult(takePictureIntent, REQUEST_TAKE_IMAGE)
            }
        }
    }

    // gets stored on /storage/emulated/0/Android/data/com.limbo.camerademo/files/Pictures/{image_here}.png
    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile(
            "HACKSC_$timeStamp",
            ".jpg",
            storageDir
        ).apply {
            Log.d(TAG, "createImageFile currentPhotoPath: $currentPhotoPath")
            Log.d(TAG, "createImageFile absolutePath: $absolutePath")
            currentPhotoPath = absolutePath
            mCurrentPhotoPath = absolutePath
        }
    }

    override fun onRegisterObserver(fileUri: Uri) {
        // get the file hereeeeeeee
        Log.d(TAG, ".onRegisterObserver called")
        val imageFile = File(fileUri.path)
        photoViewModel.uploadPhoto(imageFile).observe(this, Observer {
            // display ui shit here
            // display ingredients thumbnail with ingredient name
            Log.d(TAG, "gooby pls")

        })

    }

    private fun checkPersmission(): Boolean {
        return (ContextCompat.checkSelfPermission(this, CAMERA) ==
                PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
            android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE, CAMERA),
            PERMISSION_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {

                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    &&grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    dispatchTakePictureIntent()

                } else {
                    Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show()
                }
                return
            }

            else -> {

            }
        }
    }
}
