package mx.com.ernox.albums.database.tables

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Photo(@PrimaryKey var filePath : String = "",
                 @Ignore var isSelected: Boolean = false) : Parcelable