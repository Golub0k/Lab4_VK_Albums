package com.example.lab4_vk_albums

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val userIdEdit: EditText = findViewById(R.id.Input_user_id)
        val getAlbumsButton: Button = findViewById(R.id.get_albums_button)
        getAlbumsButton.setOnClickListener {
            val userId = userIdEdit.text.toString()
            if (userId.isNotEmpty()) {
                val intent = Intent(this@MainActivity, AlbumsActivity::class.java)
                intent.putExtra("user_id", userId)
                startActivity(intent)
            }
            else{
                val text = R.string.empty_id
                val duration = Toast.LENGTH_SHORT

                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()
            }
        }
    }
}