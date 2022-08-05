package com.example.AndroidProjectGLSIB

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.AndroidProjectGLSIB.db.productsDatabase
import com.example.AndroidProjectGLSIB.model.UserData
import com.malkinfo.editingrecyclerview.R

class product_details : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        val back = findViewById<Button>(R.id.back)

        back.setOnClickListener{
            val intentToHome: Intent = Intent(this, MainActivity::class.java)
            startActivity(intentToHome)
        }
    }

}