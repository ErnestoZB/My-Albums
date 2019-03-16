package mx.com.ernox.albums.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import mx.com.ernox.albums.database.tables.Album

@Dao
interface AlbumDao {

    @Insert
    fun insert(album : Album)

    @Query("Select * FROM Album")
    fun getAll() : List<Album>

    @Query("SELECT COUNT(1) FROM Album WHERE name=:albumName")
    fun getAlbum(albumName : String) : Int

}