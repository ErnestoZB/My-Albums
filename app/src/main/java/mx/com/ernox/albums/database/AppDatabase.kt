package mx.com.ernox.albums.database

import androidx.room.Database
import androidx.room.RoomDatabase
import mx.com.ernox.albums.database.dao.AlbumDao
import mx.com.ernox.albums.database.dao.AlbumsPhotosDao
import mx.com.ernox.albums.database.dao.PhotoDao
import mx.com.ernox.albums.database.tables.Album
import mx.com.ernox.albums.database.tables.AlbumsPhotos
import mx.com.ernox.albums.database.tables.Photo

@Database(entities = [Album::class, Photo::class, AlbumsPhotos::class],
          version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getAlbumDao() : AlbumDao

    abstract fun getPhotoDao() : PhotoDao

    abstract fun getAlbumsPhotosDao() : AlbumsPhotosDao
}