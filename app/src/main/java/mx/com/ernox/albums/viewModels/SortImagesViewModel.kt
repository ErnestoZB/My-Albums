package mx.com.ernox.albums.viewModels

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import mx.com.ernox.albums.database.dao.AlbumDao
import mx.com.ernox.albums.database.dao.AlbumsPhotosDao
import mx.com.ernox.albums.database.dao.PhotoDao
import mx.com.ernox.albums.database.tables.Album
import mx.com.ernox.albums.database.tables.AlbumsPhotos
import mx.com.ernox.albums.extensions.Event

class SortImagesViewModel(private val albumDao: AlbumDao,
                          private val photosDao: PhotoDao,
                          private val albumsPhotosDao: AlbumsPhotosDao) : ViewModel() {

    var isProgressVisible = ObservableField<Boolean>(false)

    val launchAllAlbumsView = MutableLiveData<Event<Boolean>>()

    fun onSaveImagesClicked(album: Album) = GlobalScope.async {

        isProgressVisible.set(true)

        saveAlbumWithPhotos(album)

        isProgressVisible.set(false)

        launchAllAlbumsView.postValue(Event(true))
    }

    private fun saveAlbumWithPhotos(album: Album) {

        albumDao.insert(album)

        for (photo in album.photos) {
            photosDao.insert(photo)
            albumsPhotosDao.insert(AlbumsPhotos(album.name, photo.filePath))
        }
    }
}