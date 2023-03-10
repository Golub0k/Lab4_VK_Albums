package com.example.lab4_vk_albums.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lab4_vk_albums.AlbumsActivity
import com.example.lab4_vk_albums.PhotosActivity
import com.example.lab4_vk_albums.R
import com.example.lab4_vk_albums.models.Album


class AlbumsAdapter(private val userId: String, private val context: Context) :
    RecyclerView.Adapter<AlbumsAdapter.AlbumsHolder>() {

    private var albums = ArrayList<Album>()
    private var curAlbumsSize = 0
    var maxSize = 0
    var uploading = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsHolder {
        val view: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.album_item, parent, false)
        return AlbumsHolder(view)
    }

    override fun onBindViewHolder(holder: AlbumsHolder, position: Int) {
        holder.albumName.text = albums[position].title
        holder.albumImage.setOnClickListener(openAlbum(position))
        holder.albumCard.setOnClickListener(openAlbum(position))

        Glide.with(context).load(albums[position].thumbSrc)
            .placeholder(android.R.drawable.progress_indeterminate_horizontal)
            .error(R.drawable.ic_launcher_foreground)
            .into(holder.albumImage)

        if (curAlbumsSize < maxSize && !uploading && position >= curAlbumsSize - 5) {
            uploading = true
            (context as AlbumsActivity).uploadAlbums(this, userId, albums.size, 20)
        }
    }


    private fun openAlbum(position: Int): (v: View) -> Unit = {
        val intent = Intent(context, PhotosActivity::class.java)
        intent.putExtra("album_id", albums[position].id)
        intent.putExtra("album_name", albums[position].title)
        intent.putExtra("user_id", userId)
        context.startActivity(intent)
    }

    override fun getItemCount(): Int {
        return albums.size
    }


    fun addData(albums: List<Album>) {
        val prevAlbumsSize = this.albums.size
        this.albums += albums
        curAlbumsSize = this.albums.size
        notifyItemRangeInserted(prevAlbumsSize, albums.size)
    }

    inner class AlbumsHolder(item: View) : RecyclerView.ViewHolder(item) {
        var albumName: TextView = item.findViewById(R.id.album_name_text)
        var albumImage: ImageButton = item.findViewById(R.id.album_image_button)
        var albumCard: CardView = item.findViewById(R.id.album_card)
    }
}