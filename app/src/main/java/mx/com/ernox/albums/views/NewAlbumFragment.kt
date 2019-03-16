package mx.com.ernox.albums.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_new_album.*
import mx.com.ernox.albums.R
import mx.com.ernox.albums.database.tables.Album
import mx.com.ernox.albums.extensions.showAlertDialog
import mx.com.ernox.albums.viewModels.NewAlbumViewModel
import org.koin.android.ext.android.inject

class NewAlbumFragment : Fragment() {

    private val viewModel: NewAlbumViewModel by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_new_album, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.title = "Nuevo Álbum"

        setupListeners()
    }

    private fun setupListeners()
    {
        choose_Images.setOnClickListener {
            onImagesButtonClicked()
        }

        viewModel.errorMessage.observe(this, Observer {
            it?.getContentIfNotHandled()?.apply {
                activity?.showAlertDialog(this)
            }
        })

        viewModel.album.observe(this, Observer {
            it?.getContentIfNotHandled()?.apply {
                showChooseImagesFragment(this)
            }
        })
    }

    private fun onImagesButtonClicked() {
        val albumName = album_name.text.toString()
        viewModel.onImageButtonClicked(albumName)
    }

    private fun showChooseImagesFragment(album : Album) {

        val bundle = Bundle()
        bundle.putParcelable("Album", album)

        Navigation.findNavController(requireActivity(), R.id.fragment_layout)
                  .navigate(R.id.chooseImagesFragment, bundle)
    }
}