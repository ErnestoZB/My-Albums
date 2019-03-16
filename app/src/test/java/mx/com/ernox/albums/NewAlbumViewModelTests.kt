package mx.com.ernox.albums

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.*
import mx.com.ernox.albums.database.dao.AlbumDao
import mx.com.ernox.albums.viewModels.NewAlbumViewModel
import org.junit.Test
import org.junit.Before
import org.junit.Rule
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class NewAlbumViewModelTests {

    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var albumDao: AlbumDao

    private val viewModel : NewAlbumViewModel by lazy {
        NewAlbumViewModel(albumDao)
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun showEmptyNameError_whenAlbumNameIsEmpty() {

        // Setup
        val albumName = ""

        runBlocking {

            // Act
            viewModel.onImageButtonClicked(albumName).await()

            // Verify
            assertEquals(viewModel.noAlbumNameError, viewModel.errorMessage.value?.peekContent())
        }
    }


    @Test
    fun showDuplicateNameError_whenAlbumNameIsAlreadyUsed() {

        // Setup
        val albumName = "Fotos"
        `when`(albumDao.getAlbum(albumName)).thenReturn(1)

        runBlocking {

            // Act
            viewModel.onImageButtonClicked(albumName).await()

            // Verify
            assertEquals(viewModel.duplicateAlbumError, viewModel.errorMessage.value?.peekContent())
        }
    }

    @Test
    fun shouldCreateAlbum_IfNameIsValid() {
        // Setup
        val albumName = "Fotos"
        `when`(albumDao.getAlbum(albumName)).thenReturn(0)

        runBlocking {

            // Act
            viewModel.onImageButtonClicked(albumName).await()

            // Verify
            assertNotNull(viewModel.album.value)
        }
    }
}