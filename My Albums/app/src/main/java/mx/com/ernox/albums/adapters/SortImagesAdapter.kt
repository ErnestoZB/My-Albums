package mx.com.ernox.albums.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mx.com.ernox.albums.R
import java.util.*
import mx.com.ernox.albums.adapters.holders.SortImageViewHolder
import mx.com.ernox.albums.adapters.interfaces.ItemTouchHelperAdapter
import mx.com.ernox.albums.adapters.interfaces.OnStartDragListener
import mx.com.ernox.albums.database.tables.Photo


class SortImagesAdapter(private val images: ArrayList<Photo>, private val dragListener : OnStartDragListener) : RecyclerView.Adapter<SortImageViewHolder>(),
                                                                                                                ItemTouchHelperAdapter {

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        Collections.swap(images, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SortImageViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.holder_sortable_image, parent, false)
        return SortImageViewHolder(v, dragListener)
    }

    override fun onBindViewHolder(holder: SortImageViewHolder, position: Int) {
        holder.bindItems(images[position])
    }

    override fun getItemCount(): Int {
        return images.size
    }
}