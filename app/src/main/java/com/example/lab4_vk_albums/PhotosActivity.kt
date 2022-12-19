package com.example.lab4_vk_albums

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.lab4_vk_albums.adapters.PhotosAdapter
import com.example.lab4_vk_albums.api.RetrofitClient
import com.example.lab4_vk_albums.api.VkApi
import com.example.lab4_vk_albums.models.photosModel.GetPhotosResponse
import com.example.lab4_vk_albums.models.photosModel.Photo
import com.google.android.flexbox.*
import retrofit2.Response


class PhotosActivity : AppCompatActivity() {
    private lateinit var errorLabel: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photos)

        val photosView: RecyclerView = findViewById(R.id.photos_view)
        errorLabel = findViewById(R.id.photos_placeholder)

        val albumId: Int = intent.getIntExtra("album_id", -1)
        val userId: String = intent.getStringExtra("user_id")!!
        val albumName: String = intent.getStringExtra("album_name")!!
        title = albumName

        // Great layout
        photosView.layoutManager = FlexboxLayoutManager(this@PhotosActivity).apply {
            justifyContent = JustifyContent.CENTER
            alignItems = AlignItems.CENTER
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
        }
        val adapter = PhotosAdapter(userId, albumId, this@PhotosActivity)
        photosView.adapter = adapter

        // Upload first photos package
        uploadPictures(adapter, userId, 0, 50, albumId)
    }

    fun uploadPictures(
        adapter: PhotosAdapter,
        userId: String,
        offset: Int,
        count: Int,
        albumId: Int,
    ) {
        lifecycleScope.launchWhenCreated {
            val api = RetrofitClient.getInstance().create(VkApi::class.java)
            var response: Response<GetPhotosResponse>? = null

            try {
                response = api.getPhotos(
                    userId,
                    albumId,
                    offset,
                    count,
                    1
                )
                if (response.isSuccessful) {
                    if (response.body()?.response?.count == 0) {
                        errorLabel.text = getString(R.string.no_photos)
                        errorLabel.visibility = View.VISIBLE
                    } else {
                        val photos: List<Photo> = response.body()!!.response.items
                        val photosUrl = ArrayList<String>()
                        for (photo in photos) {
                            photosUrl.add(photo.sizes[6].url)
                        }

                        adapter.addData(photosUrl)
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