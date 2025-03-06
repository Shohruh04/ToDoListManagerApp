package uz.shokhrukh.todolistmanager.presentation.utils

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date

fun Long.toDateString(): String {
    val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm")
    return sdf.format(this)
}

private fun Context.createImageFile(path: (String) -> Unit): File? {
    val timeStamp: String = SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(Date())
    val imageFileName = timeStamp + "_"
    val storageDir: File? =
        this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    val image = File.createTempFile(
        imageFileName,
        ".jpg",
        storageDir
    ).apply {
        path.invoke(absolutePath)
    }
    return image
}

fun Context.cameraIntent(imagePath: (String) -> Unit, intent: (Intent) -> Unit) {
    val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    if (takePictureIntent.resolveActivity(this.packageManager) != null) {
        var photoFile: File? = null
        try {
            photoFile = createImageFile {
                imagePath.invoke(it)
            }
        } catch (ex: IOException) {
            Log.d("Error", ex.message.toString())
        }
        if (photoFile != null) {
            val photoURI = FileProvider.getUriForFile(
                this,
                "${this.packageName}.fileprovider",
                photoFile
            )
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.invoke(takePictureIntent)
        }
    }
}

fun Context.checkCameraPermission() =
    checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
