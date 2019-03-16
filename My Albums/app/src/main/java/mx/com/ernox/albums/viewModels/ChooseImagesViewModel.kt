package mx.com.ernox.albums.viewModels

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import mx.com.ernox.albums.database.tables.Photo
import mx.com.ernox.albums.extensions.SingleLiveEvent

class ChooseImagesViewModel(val context: Context) : ViewModel()  {

    private val imageColumns = arrayOf(MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID)
    private val imageOrderBy = MediaStore.Images.Media._ID

    val noImagesSelectedError = "No has seleccionado ninguna imagen"
    val noReadPermissionError = "La app no tiene permiso para acceder a tus imágenes"
    val noAvailableImagesError = "No se encontraron imágenes en tu dispositivo"

    var isProgressVisible = ObservableField<Boolean>(false)

    val errorMessage = MutableLiveData<String>()

    val availableImages = MutableLiveData<ArrayList<Photo>>()

    val selectedImages = MutableLiveData<List<Photo>>()

    val launchSortImagesActivity = SingleLiveEvent<Boolean>()

    fun onReadPermissionDenied() {
        errorMessage.postValue(noReadPermissionError)
    }

    fun onSortImagesClicked() {

        if(!areImagesSelected())
        {
            errorMessage.postValue(noImagesSelectedError)
            return
        }

        filterSelectedImages()

        launchSortImagesActivity.postValue(true)
    }

    fun getDeviceImages() = GlobalScope.async {

        isProgressVisible.set(true)

        val internalImages = getImagesFromUri(MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        val externalImages = getImagesFromUri(MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        val allImages = ArrayList<Photo>()
        allImages.addAll(internalImages)
        allImages.addAll(externalImages)

        if(allImages.count() > 0)
        {
            availableImages.postValue(allImages)
        }
        else
        {
            errorMessage.postValue(noAvailableImagesError)
        }

        isProgressVisible.set(false)
    }

    private fun areImagesSelected(): Boolean {
        return availableImages.value?.any { it.isSelected } ?: false
    }

    private fun filterSelectedImages() {
        selectedImages.postValue( availableImages.value?.filter { it.isSelected } )
    }

    private fun getImagesFromUri(imagesPath : Uri) : ArrayList<Photo> {

        val cursor = context.contentResolver.query(imagesPath,
            imageColumns,
            null,
            null,
            imageOrderBy)

        val images = ArrayList<Photo>()

        if(cursor != null)
        {
            for (i in 0 until cursor.count)
            {
                cursor.moveToPosition(i)

                val dataColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA)

                val path = cursor.getString(dataColumnIndex)

                val image = Photo(path)

                images.add(image)
            }

            cursor.close()
        }

        return images
    }
}