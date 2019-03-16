package mx.com.ernox.albums.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mx.com.ernox.albums.adapters.holders.BindableViewHolder

class GenericAdapter<I, H : BindableViewHolder<I>>(private val layoutId : Int,
                                                   private val items: ArrayList<I>,
                                                   private val factory : (View) -> H) : RecyclerView.Adapter<H>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): H {
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)

        return factory(view)
    }

    override fun onBindViewHolder(holder: H, position: Int) {

        holder.bindItems(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}