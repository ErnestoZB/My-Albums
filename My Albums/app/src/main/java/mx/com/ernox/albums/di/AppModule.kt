package mx.com.ernox.albums.di

import androidx.room.Room
import mx.com.ernox.albums.database.AppDatabase
import mx.com.ernox.albums.viewModels.AlbumsViewModel
import mx.com.ernox.albums.viewModels.ChooseImagesViewModel
import mx.com.ernox.albums.viewModels.NewAlbumViewModel
import mx.com.ernox.albums.viewModels.SortImagesViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {

    viewModel { AlbumsViewModel(get(), get()) }

    viewModel { NewAlbumViewModel(get()) }

    viewModel { ChooseImagesViewModel( androidApplication() ) }

    viewModel { SortImagesViewModel(get(), get(), get()) }

    single {
        Room.databaseBuilder(androidApplication(), AppDatabase::class.java, "app.db").build()
    }

    single { get<AppDatabase>().getAlbumDao() }

    single { get<AppDatabase>().getPhotoDao() }

    single { get<AppDatabase>().getAlbumsPhotosDao() }

}