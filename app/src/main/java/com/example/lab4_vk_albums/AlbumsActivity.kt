package com.example.lab4_vk_albums

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.lab4_vk_albums.adapters.AlbumsAdapter
import com.example.lab4_vk_albums.api.RetrofitClient
import com.example.lab4_vk_albums.api.VkApi
import com.example.lab4_vk_albums.models.albumModel.Album
import com.example.lab4_vk_albums.models.albumModel.GetAlbumResponse
import com.google.android.flexbox.*
import retrofit2.Response

class AlbumsActivity : AppCompatActivity() {
    private lateinit var errorLabel: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_albums)

        val albumsView: RecyclerView = findViewById(R.id.albums_view)
        errorLabel = findViewById(R.id.albums_placeholder)

        val userId = intent.getStringExtra("user_id")!!

        // Great layout
        albumsView.layoutManager = FlexboxLayoutManager(this@AlbumsActivity).apply {
            justifyContent = JustifyContent.SPACE_AROUND
            alignItems = AlignItems.CENTER
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
        }
        val adapter = AlbumsAdapter(userId, this@AlbumsActivity)
        albumsView.adapter = adapter

        // Upload first albums package
        uploadAlbums(adapter, userId, 0, 20)
    }

    /**
     * Upload new albums package of 'count' albums beginning from 'offset' asynchronously
     */
    fun uploadAlbums(
        adapter: AlbumsAdapter,
        userId: String,
        offset: Int,
        count: Int,
    ) {
        lifecycleScope.launchWhenCreated {
            val api = RetrofitClient.getInstance().create(VkApi::class.java)
            var response: Response<GetAlbumResponse>? = null

            try {
                response = api.getAlbums(userId, offset, count, 1)
                if (response.isSuccessful) {
                    if (response.body()?.response?.count == 0) {
                        errorLabel.text = getString(R.string.no_albums)
                        errorLabel.visibility = View.VISIBLE
                    } else {
                        val albums: List<Album> = response.body()!!.response.items
                        adapter.addData(albums)
                        adapter.uploading = false
                        adapter.maxSize = response.body()!!.response.count
                    }
                } else {
                    errorLabel.text = getString(R.string.internet_con)
                    errorLabel.visibility = View.VISIBLE
                }
            } catch (Ex: Exception) {
                val code = response?.body()?.error?.code
                when (code) {
                    5 -> errorLabel.text =
                        getString(R.string.token_expired_error)
                    6 -> errorLabel.text =
                        getString(R.string.too_many_requests_error)
                    10 -> errorLabel.text = getString(R.string.server_error)
                    15 -> errorLabel.text =
                        getString(R.string.permission_denied_error)
                    18 -> errorLabel.text = getString(R.string.user_blocked_error)
                    100 -> errorLabel.text =
                        getString(R.string.incorrect_param_error)
                    200 -> errorLabel.text =
                        getString(R.string.album_permission_denied_error)
                    1116 -> errorLabel.text =
                        getString(R.string.token_invalid_error)
                    else -> errorLabel.text = getString(R.string.unknown_error)
                }
                errorLabel.visibility = View.VISIBLE
                Ex.localizedMessage?.let { Log.e("Error", it)}
            }
        }
    }
}