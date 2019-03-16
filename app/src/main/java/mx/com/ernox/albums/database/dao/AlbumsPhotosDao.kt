package mx.com.ernox.albums.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import mx.com.ernox.albums.database.tables.AlbumsPhotos
import mx.com.ernox.albums.database.tables.Photo

@Dao
interface AlbumsPhotosDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(albumsPhotos : AlbumsPhotos)

    @Query("SELECT * FROM Photo INNER JOIN AlbumsPhotos ON Photo.filePath=AlbumsPhotos.filePath WHERE AlbumsPhotos.albumName=:albumName")
    fun getAlbumPhotos(albumName : String) : List<Photo>
}