//package mx.com.ernox.albums.network
//
//import mx.com.ernox.albums.network.api.AlbumApi
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import okhttp3.OkHttpClient
//import java.util.concurrent.TimeUnit
//
//
//class NetworkManager {
//
//    companion object {
//
//        private const val serverUrl = "http://10.0.2.2:8080"
//
//        private val retrofit: Retrofit by lazy {
//
//            val client = OkHttpClient.Builder()
//                .readTimeout(5, TimeUnit.SECONDS)
//                .writeTimeout(5, TimeUnit.SECONDS)
//                .connectTimeout(5, TimeUnit.SECONDS)
//                .build()
//
//            Retrofit.Builder()
//                .baseUrl(serverUrl)
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(client)
//                .build()
//        }
//
//        fun getAlbumApi() : AlbumApi {
//            return retrofit.create(AlbumApi::class.java)
//        }
//    }
//
//
//
//}