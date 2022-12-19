package com.example.lab4_vk_albums.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lab4_vk_albums.PhotosActivity
import com.example.lab4_vk_albums.R

class PhotosAdapter(
    private val userId: String,
    private val albumId: Int,
    private val context: Context
) :
    RecyclerView.Adapter<PhotosAdapter.PhotosHolder>() {

    private var photosUrl = ArrayList<String>()
    private var curPhotosSize = 0
    var maxSize = 0
    var uploading = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.photo_item, parent, false)
        return PhotosHolder(view)
    }


    override fun onBindViewHolder(holder: PhotosHolder, position: Int) {
        Glide.with(context).load(photosUrl[position])
            .placeholder(android.R.drawable.progress_indeterminate_horizontal)
            .error(R.drawable.ic_launcher_foreground)
            .into(holder.photoImage)

        if (curPhotosSize < maxSize && !uploading && position >= curPhotosSize - 10) {
            uploading = true
            (context as PhotosActivity).uploadPictures(
                this,
                userId,
                photosUrl.size,
                50,
                albumId
            )
        }
    }

    override fun getItemCount(): Int {
        return photosUrl.size
    }

    fun addData(photosUrl: List<String>) {
        val prevPhotosSize = this.photosUrl.size
        this.photosUrl += photosUrl
        curPhotosSize = this.photosUrl.size
        notifyItemRangeInserted(prevPhotosSize, photosUrl.size)
    }

    inner class PhotosHolder(item: View) : RecyclerView.ViewHolder(item) {
        var photoImage: ImageView = item.findViewById(R.id.photo_image)
    }
}