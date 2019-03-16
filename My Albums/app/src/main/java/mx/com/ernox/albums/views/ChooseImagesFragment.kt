package mx.com.ernox.albums.views

import android.Manifest
import android.os.Bundle
import kotlinx.android.synthetic.main.fragment_choose_images.*
import android.content.pm.PackageManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import mx.com.ernox.albums.R
import mx.com.ernox.albums.adapters.GenericAdapter
import mx.com.ernox.albums.adapters.holders.ChooseImageViewHolder
import mx.com.ernox.albums.database.tables.Album
import mx.com.ernox.albums.database.tables.Photo
import mx.com.ernox.albums.databinding.FragmentChooseImagesBinding
import mx.com.ernox.albums.extensions.showAlertDialog
import mx.com.ernox.albums.viewModels.ChooseImagesViewModel
import org.koin.android.ext.android.inject

class ChooseImagesFragment : Fragment() {

    private val requestReadExternalStorageCode = 1

    private lateinit var album : Album

    private val viewModel: ChooseImagesViewModel by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentChooseImagesBinding>(inflater,
                                                                           R.layout.fragment_choose_images,
                                                                           container,
                                                             false)
        binding.viewModel = viewModel


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.title = "Selección de Fotos"

        setListeners()

        checkReadExternalPermission()
    }

    private fun setAlbumFromExtras() {
        album = arguments?.getParcelable("Album") ?: Album()
    }

    private fun setListeners() {
        sort_images.setOnClickListener {
            viewModel.onSortImagesClicked()
        }

        viewModel.errorMessage.observe(this, Observer {
            it?.apply {
                activity?.showAlertDialog(it)
            }
        })

        viewModel.availableImages.observe(this, Observer {
            it?.isNotEmpty()?.apply {
                setRecyclerManager()
                setRecyclerAdapter(it)
            }
        })

        viewModel.selectedImages.observe(this, Observer {
            it?.isNotEmpty()?.apply {
                album.photos = ArrayList(it)
            }
        })

        viewModel.launchSortImagesActivity.observe(this, Observer {
            it?.apply {
                launchSortImagesActivity()
            }
        })
    }

    private fun checkReadExternalPermission()
    {
        if (ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED)
        {
            onReadExternalPermissionGranted()
        }
        else
        {
            requestReadExternalPermission()
        }
    }

    private fun onReadExternalPermissionGranted() {
        setAlbumFromExtras()
        viewModel.getDeviceImages()
    }

    private fun requestReadExternalPermission() {
        val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        requestPermissions(permission, requestReadExternalStorageCode)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {

        when (requestCode)
        {
            requestReadExternalStorageCode -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    onReadExternalPermissionGranted()
                }
                else
                {
                    viewModel.onReadPermissionDenied()
                }
                return
            }
        }
    }

    private fun launchSortImagesActivity() {
        val bundle = Bundle()
        bundle.putParcelable("Album", album)

       Navigation.findNavController(requireActivity(), R.id.fragment_layout)
                 .navigate(R.id.sortImagesFragment, bundle)
    }

    private fun setRecyclerManager() {
        images.layoutManager = GridLayoutManager(context, 3)
        images.setHasFixedSize(true)
    }

    private fun setRecyclerAdapter(availableImages : ArrayList<Photo>) {

        val items = ArrayList(availableImages)

        val adapter = GenericAdapter<Photo, ChooseImageViewHolder>(R.layout.holder_selectable_image, items) {
            ChooseImageViewHolder(it)
        }

        images.adapter = adapter
    }
}