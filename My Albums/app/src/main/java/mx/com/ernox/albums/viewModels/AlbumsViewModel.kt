package mx.com.ernox.albums.viewModels

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import mx.com.ernox.albums.database.dao.AlbumDao
import mx.com.ernox.albums.database.dao.AlbumsPhotosDao
import mx.com.ernox.albums.database.tables.Album
import mx.com.ernox.albums.extensions.SingleLiveEvent

class AlbumsViewModel(private val albumDao: AlbumDao,
                      private val albumsPhotosDao: AlbumsPhotosDao) : ViewModel() {

    var isProgressVisible = ObservableField<Boolean>(false)

    val albums = MutableLiveData<List<Album>>()

    val launchNewAlbumView = SingleLiveEvent<Boolean>()

    fun initAsync() = GlobalScope.async {

        isProgressVisible.set(true)

        getAlbums()

        isProgressVisible.set(false)
    }

    private fun getAlbums() {

        val userAlbums = albumDao.getAll()

        userAlbums.forEach {

            val albumName = it.name
            val pictures = albumsPhotosDao.getAlbumPhotos(albumName)

            it.photos = ArrayList(pictures)
        }

        albums.postValue(userAlbums)
    }

    fun onNewAlbumClicked() {
        launchNewAlbumView.postValue(true)
    }
}