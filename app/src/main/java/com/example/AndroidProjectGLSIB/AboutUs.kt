package com.example.AndroidProjectGLSIB

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.malkinfo.editingrecyclerview.R

class AboutUs: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)
        val back = findViewById<Button>(R.id.back)

        back.setOnClickListener{
            val intentToHome: Intent = Intent(this, MainApp::class.java)
            startActivity(intentToHome)
        }

    }

}