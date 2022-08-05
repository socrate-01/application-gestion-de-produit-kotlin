package com.example.AndroidProjectGLSIB

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.malkinfo.editingrecyclerview.R

class MainApp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_app)

        val getstarted = findViewById<Button>(R.id.getstarted)
        val aboutUs = findViewById<Button>(R.id.aboutus)

        getstarted.setOnClickListener{
            val intentToHome: Intent = Intent(this, MainActivity::class.java)
            startActivity(intentToHome)
        }

        aboutUs.setOnClickListener{
            val intentAboutUs: Intent = Intent(this, AboutUs::class.java)
            startActivity(intentAboutUs)
        }
    }
}