package mx.com.ernox.albums.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import mx.com.ernox.albums.database.dao.AlbumDao
import mx.com.ernox.albums.database.tables.Album
import mx.com.ernox.albums.extensions.SingleLiveEvent

class NewAlbumViewModel(private val albumDao: AlbumDao) : ViewModel() {

    val noAlbumNameError = "Debes escribir un nombre"
    val duplicateAlbumError = "Ya existe un álbum con el mismo nombre"

    val errorMessage = MutableLiveData<String>()
    val album = SingleLiveEvent<Album>()

    fun onImageButtonClicked(albumName: String) = GlobalScope.async {

        when
        {
            isNameEmpty(albumName) -> errorMessage.postValue(noAlbumNameError)

            isNameDuplicate(albumName) -> errorMessage.postValue(duplicateAlbumError)

            else -> album.postValue( Album(albumName) )
        }
    }

    private fun isNameDuplicate(name: String) : Boolean {
        return albumDao.getAlbum(name) == 1
    }

    private fun isNameEmpty(name: String): Boolean {
        return name.isNullOrEmpty()
    }
}