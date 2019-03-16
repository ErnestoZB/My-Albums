package mx.com.ernox.albums.views


import android.os.Bundle
import mx.com.ernox.albums.adapters.GenericAdapter
import mx.com.ernox.albums.adapters.holders.AlbumViewHolder
import mx.com.ernox.albums.database.tables.Album
import mx.com.ernox.albums.viewModels.AlbumsViewModel
import org.koin.android.ext.android.inject
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_albums.*
import mx.com.ernox.albums.R
import mx.com.ernox.albums.databinding.FragmentAlbumsBinding

class AlbumsFragment : Fragment() {

    private val viewModel: AlbumsViewModel by inject()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<FragmentAlbumsBinding>(inflater,
                                                                     R.layout.fragment_albums,
                                                                     container,
                                                                    false)
        binding.viewModel = viewModel


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.title = "Mis Álbumes"

        setupListeners()

        viewModel.initAsync()
    }

    private fun setupListeners() {
        addAlbumButton.setOnClickListener {
            viewModel.onNewAlbumClicked()
        }

        viewModel.albums.observe(this, Observer {
            it?.apply {
                onGetAlbumsComplete(it)
            }
        })

        viewModel.launchNewAlbumView.observe(this, Observer {
            it?.apply {
                if(it)
                showCreateAlbumFragment()
            }
        })
    }

    private fun onGetAlbumsComplete(availableAlbums: List<Album>) {

        if(availableAlbums.isNotEmpty())
        {
            setRecyclerManager()
            setRecyclerAdapter(availableAlbums)
        }
    }

    private fun setRecyclerManager() {
        albums.layoutManager = LinearLayoutManager(activity)
        albums.setHasFixedSize(true)
    }

    private fun setRecyclerAdapter(availableAlbums: List<Album>) {

        val items = ArrayList(availableAlbums)

        val adapter = GenericAdapter<Album, AlbumViewHolder>(R.layout.holder_album, items) {
            AlbumViewHolder(it)
        }

        albums.adapter = adapter
    }

    private fun showCreateAlbumFragment() {
        Navigation.findNavController(requireActivity(), R.id.fragment_layout)
                  .navigate(R.id.newAlbumFragment)
    }
}