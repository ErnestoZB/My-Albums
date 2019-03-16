package mx.com.ernox.albums.views

import android.os.Bundle
import kotlinx.android.synthetic.main.fragment_sort_images.*
import mx.com.ernox.albums.adapters.interfaces.OnStartDragListener
import mx.com.ernox.albums.adapters.SortImagesAdapter
import mx.com.ernox.albums.adapters.helpers.ItemTouchHelperCallback
//import okhttp3.MediaType
//import okhttp3.MultipartBody
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import okhttp3.RequestBody
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import mx.com.ernox.albums.R
import mx.com.ernox.albums.database.tables.Album
import mx.com.ernox.albums.databinding.FragmentSortImagesBinding
import mx.com.ernox.albums.viewModels.SortImagesViewModel
import org.koin.android.ext.android.inject


class SortImagesFragment : Fragment(), OnStartDragListener /*, Callback<Void> */ {

    // private val serverError = "Ocurrió un error al intentar guardar tu álbum"

    private lateinit var itemTouchHelper: ItemTouchHelper

    private lateinit var album : Album

    private val viewModel: SortImagesViewModel by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentSortImagesBinding>(inflater,
                                                                         R.layout.fragment_sort_images,
                                                                         container,
                                                                        false)
        binding.viewModel = viewModel


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.title = "Ordenar Fotos"

        setAlbumFromExtras()
        setListeners()
        setRecyclerManager()
        setRecyclerAdapter()
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
        itemTouchHelper.startDrag(viewHolder)
    }

    private fun setAlbumFromExtras() {
        album = arguments?.getParcelable("Album") ?: Album()
    }

    private fun setListeners() {
        save_album.setOnClickListener {
            onSaveImagesClicked()
        }

        viewModel.launchAllAlbumsView.observe(this, Observer {
            it?.apply {
                showAlbumsFragment()
            }
        })
    }

    private fun onSaveImagesClicked() {
        viewModel.onSaveImagesClicked(album)
    }

    private fun showAlbumsFragment() {
        Navigation.findNavController(requireActivity(), R.id.fragment_layout)
                  .navigate(R.id.albumsFragment)
    }

    private fun setRecyclerManager() {
        images.layoutManager = GridLayoutManager(context, 3)
        images.setHasFixedSize(true)
    }

    private fun setRecyclerAdapter() {
        val adapter = SortImagesAdapter(album.photos, this)
        images.adapter = adapter

        val callback = ItemTouchHelperCallback(adapter)
        itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(images)
    }

//    private fun uploadAlbum() {
//        val albumApi = NetworkManager.getAlbumApi()
//
//        val multiPartFiles = getMultiPartFiles()
//
//        val call : Call<Void> = albumApi.uploadAlbum(album.name, multiPartFiles)
//        call.enqueue(this)
//    }
//
//    private fun getMultiPartFiles() : ArrayList<MultipartBody.Part> {
//        val multiPartFiles = ArrayList<MultipartBody.Part>()
//
//        album.photos.forEach{
//            val requestImage = RequestBody.create(MediaType.parse("image/*"), it.filePath)
//            multiPartFiles.add(MultipartBody.Part.createFormData("photos", it.filePath, requestImage))
//        }
//
//        return multiPartFiles
//    }
//
//    override fun onFailure(call: Call<Void>, t: Throwable) {
//        hideLoadingDialog()
//
//        showAlertDialog(serverError)
//    }
//
//    override fun onResponse(call: Call<Void>, response: Response<Void>) {
//
//        if(response.isSuccessful)
//        {
//            showAlbumsFragment()
//        }
//        else
//        {
//            showAlertDialog(serverError)
//        }
//    }
}