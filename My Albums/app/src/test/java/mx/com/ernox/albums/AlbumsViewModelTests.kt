package mx.com.ernox.albums

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.*
import mx.com.ernox.albums.database.dao.AlbumDao
import mx.com.ernox.albums.database.dao.AlbumsPhotosDao
import mx.com.ernox.albums.database.tables.Album
import mx.com.ernox.albums.viewModels.AlbumsViewModel
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class AlbumsViewModelTests {

    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var albumDao: AlbumDao

    @Mock
    private lateinit var albumsPhotosDao: AlbumsPhotosDao


    private val viewModel : AlbumsViewModel by lazy {
        AlbumsViewModel(albumDao, albumsPhotosDao)
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun shouldGetAlbums() {

        runBlocking {

            // Act
            viewModel.initAsync().await()

            // Verify
            verify(albumDao).getAll()
        }
    }

    @Test
    fun shouldHideProgressBar_afterGettingAlbums() {

        runBlocking {

            // Act
            viewModel.initAsync().await()

            // Assert
            assertFalse(viewModel.isProgressVisible.get() ?: true)
        }
    }

    @Test
    fun shouldGetPhotos_forEveryAlbum() {

        // Setup
        val albums = arrayListOf( Album(), Album() )
        `when`(albumDao.getAll()).thenReturn(albums)

        runBlocking {

            // Act
            viewModel.initAsync().await()

            // Verify
            verify(albumsPhotosDao, times(albums.size)).getAlbumPhotos(anyString())
        }
    }

    @Test
    fun onLaunchNewAlbumClicked_shouldLaunchNewAlbumView() {

        // Act
        viewModel.onNewAlbumClicked()

        // Assert
        assertTrue(viewModel.launchNewAlbumView.value ?: false)
    }
}