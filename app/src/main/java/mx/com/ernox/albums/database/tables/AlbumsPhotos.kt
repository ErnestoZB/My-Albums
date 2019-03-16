package mx.com.ernox.albums.database.tables

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(primaryKeys = [ "albumName", "filePath" ],
        foreignKeys = [
            ForeignKey(entity = Album::class,   parentColumns = ["name"],     childColumns = ["albumName"]),
            ForeignKey(entity = Photo::class,   parentColumns = ["filePath"], childColumns = ["filePath"])
    ]
)
class AlbumsPhotos(val albumName: String,
                   val filePath: String)