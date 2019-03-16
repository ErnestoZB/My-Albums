package mx.com.ernox.albums

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import junit.framework.TestCase.*
import mx.com.ernox.albums.database.tables.Photo
import mx.com.ernox.albums.viewModels.ChooseImagesViewModel
import org.junit.Test
import org.junit.Before
import org.junit.Rule
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ChooseImagesViewModelTests {

    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var context: Context

    private val viewModel : ChooseImagesViewModel by lazy {
        ChooseImagesViewModel(context)
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun showNoPermissionError_whenUserRejectsImagesPermission() {

        // Act
        viewModel.onReadPermissionDenied()

        // Assert
        assertEquals(viewModel.noReadPermissionError, viewModel.errorMessage.value?.peekContent())
    }

    @Test
    fun showNoImagesSelectedError_whenSortButtonIsClicked_andUserDidntSelectAnyImage() {

        // Setup
        val availableImages = arrayListOf( Photo(isSelected = false), Photo(isSelected = false) )
        viewModel.availableImages.value = availableImages

        // Act
        viewModel.onSortImagesClicked()

        // Assert
        assertEquals(viewModel.noImagesSelectedError, viewModel.errorMessage.value?.peekContent())
    }

    @Test
    fun dontFilterImages_ifNonAreSelected() {

        // Setup
        val availableImages = arrayListOf( Photo(isSelected = false), Photo(isSelected = false) )
        viewModel.availableImages.value = availableImages

        // Act
        viewModel.onSortImagesClicked()

        // Assert
        assertNull(viewModel.selectedImages.value)
    }

    @Test
    fun filterImages_ifAtLeastOneIsSelected() {

        // Setup
        val availableImages = arrayListOf( Photo(isSelected = true), Photo(isSelected = false) )
        viewModel.availableImages.value = availableImages

        // Act
        viewModel.onSortImagesClicked()

        // Assert
        assertNotNull(viewModel.selectedImages.value)
    }

    @Test
    fun launchSortImagesActivity_whenSortButtonIsClicked_ifAtLeastOneIsSelected() {

        // Setup
        val availableImages = arrayListOf( Photo(isSelected = true), Photo(isSelected = false) )
        viewModel.availableImages.value = availableImages

        // Act
        viewModel.onSortImagesClicked()

        // Assert
        assertTrue(viewModel.launchSortImagesActivity.value?.peekContent() ?: false)
    }

//    @Test
//    fun showDuplicateNameError_whenAlbumNameIsAlreadyUsed() {
//
//        // Setup
//        val albumName = "Fotos"
//        `when`(albumDao.getAlbum(albumName)).thenReturn(1)
//
//        runBlocking {
//
//            // Act
//            viewModel.onImageButtonClicked(albumName).await()
//
//            // Verify
//            assertEquals(viewModel.duplicateAlbumError, viewModel.errorMessage.value)
//        }
//    }
}