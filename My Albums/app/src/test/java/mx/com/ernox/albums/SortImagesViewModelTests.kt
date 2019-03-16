package mx.com.ernox.albums

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.anyOrNull
import junit.framework.TestCase.*
import kotlinx.coroutines.*
import mx.com.ernox.albums.database.dao.AlbumDao
import mx.com.ernox.albums.database.dao.AlbumsPhotosDao
import mx.com.ernox.albums.database.dao.PhotoDao
import mx.com.ernox.albums.database.tables.Album
import mx.com.ernox.albums.database.tables.Photo
import mx.com.ernox.albums.viewModels.SortImagesViewModel
import org.junit.Test
import org.junit.Before
import org.junit.Rule
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class SortImagesViewModelTests {

    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var albumDao: AlbumDao

    @Mock
    private lateinit var photoDao: PhotoDao

    @Mock
    private lateinit var albumsPhotosDao: AlbumsPhotosDao

    private val viewModel : SortImagesViewModel by lazy {
        SortImagesViewModel(albumDao, photoDao, albumsPhotosDao)
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    private fun createAlbum(name : String, numberOfPhotos : Int) : Album {

        val photos = arrayListOf<Photo>()

        for(i in 1..numberOfPhotos)
        {
            photos.add( Photo() )
        }

        return Album(name, photos)
    }

    @Test
    fun saveAlbumInDatabase_whenSaveImagesIsClicked() {

        // Setup
        val album = createAlbum("Fotos", 0)

        runBlocking {

            // Act
            viewModel.onSaveImagesClicked(album).await()

            // Verify
            verify(albumDao, times(1)).insert(album)
        }
    }


    @Test
    fun saveImagesInDatabase_whenSaveImagesIsClicked() {

        // Setup
        val numberOfPhotos = 2
        val album = createAlbum("Fotos", numberOfPhotos)

        runBlocking {

            // Act
            viewModel.onSaveImagesClicked(album).await()

            // Verify
            verify(photoDao, times(numberOfPhotos)).insert(anyOrNull())
        }
    }


    @Test
    fun saveImageAndAlbumInMutualTable_whenSaveImagesIsClicked() {

        // Setup
        val numberOfPhotos = 2
        val album = createAlbum("Fotos", numberOfPhotos)

        runBlocking {

            // Act
            viewModel.onSaveImagesClicked(album).await()

            // Verify
            verify(albumsPhotosDao, times(numberOfPhotos)).insert(anyOrNull())
        }
    }

    @Test
    fun shouldHideProgress_afterSavingAlbum() {

        // Setup
        val album = createAlbum("Fotos", 2)

        runBlocking {

            // Act
            viewModel.onSaveImagesClicked(album).await()

            // Verify
            assertFalse(viewModel.isProgressVisible.get() ?: false)
        }
    }

    @Test
    fun shouldLaunchAlbumsView_afterSavingAlbum() {

        // Setup
        val album = createAlbum("Fotos", 2)

        runBlocking {

            // Act
            viewModel.onSaveImagesClicked(album).await()

            // Verify
            assertTrue(viewModel.launchAllAlbumsView.value ?: false)
        }
    }
}