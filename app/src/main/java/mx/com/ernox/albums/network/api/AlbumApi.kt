//package mx.com.ernox.albums.network.api
//
//import mx.com.ernox.albums.database.tables.Album
//import okhttp3.MultipartBody
//import retrofit2.Call
//import retrofit2.http.*
//
//interface AlbumApi {
//
//    @Headers("Content-Type:application/json")
//    @GET("mx.com.ernox.albums")
//    fun getAlbums(): Call<Array<Album>>
//
//    @Multipart
//    @POST("mx.com.ernox.albums/upload")
//    fun uploadAlbum(@Part("name") name : String,
//                    @Part files : List<MultipartBody.Part>) : Call<Void>
//}