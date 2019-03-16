package mx.com.ernox.albums.adapters.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BindableViewHolder<I>(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bindItems(item: I)
}