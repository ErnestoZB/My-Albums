package mx.com.ernox.albums.adapters.holders

import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import mx.com.ernox.albums.R
import mx.com.ernox.albums.adapters.interfaces.OnStartDragListener
import mx.com.ernox.albums.database.tables.Photo

class SortImageViewHolder(itemView: View, private val dragListener: OnStartDragListener) : BindableViewHolder<Photo>(itemView), View.OnTouchListener {

    override fun bindItems(item: Photo) {
        val selectedImage = itemView.findViewById(R.id.selected_image) as ImageView

        Glide.with(itemView.context)
            .load(item.filePath)
            .placeholder(R.drawable.ic_placeholder)
            .override(150, 150)
            .into(selectedImage)

        itemView.setOnTouchListener(this)
    }

    override fun onTouch(view: View?, event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            dragListener.onStartDrag(this)
        }

        return true
    }
}