package mx.com.ernox.albums.adapters.holders

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import mx.com.ernox.albums.R
import mx.com.ernox.albums.database.tables.Photo

class ChooseImageViewHolder(itemView: View) : BindableViewHolder<Photo>(itemView) {

    private val galleryImage = itemView.findViewById(R.id.gallery_image) as ImageView

    override fun bindItems(image: Photo) {

        setImageAlpha(image.isSelected)

        Glide.with(itemView.context)
             .load(image.filePath)
             .placeholder(R.drawable.ic_placeholder)
             .override(150, 150)
             .diskCacheStrategy(DiskCacheStrategy.ALL)
             .into(galleryImage)

        itemView.setOnClickListener {
            image.isSelected = !image.isSelected
            setImageAlpha(image.isSelected)
        }
    }

    private fun setImageAlpha(isSelected : Boolean) {
        galleryImage.alpha = if(isSelected) 0.5f else 1.0f
    }
}