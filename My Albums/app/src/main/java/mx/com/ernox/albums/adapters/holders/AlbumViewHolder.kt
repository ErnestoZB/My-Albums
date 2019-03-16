package mx.com.ernox.albums.adapters.holders

import android.view.View
import android.widget.TextView
import mx.com.ernox.albums.R
import mx.com.ernox.albums.database.tables.Album

class AlbumViewHolder(itemView: View) : BindableViewHolder<Album>(itemView) {

    override fun bindItems(album: Album) {
        val albumName = itemView.findViewById(R.id.album_name) as TextView
        val picturesCount = itemView.findViewById(R.id.pictures_count) as TextView

        albumName.text = album.name

        if(album.photos != null)
        {
            picturesCount.text = " ${album.photos.size} foto(s)"
        }
    }
}