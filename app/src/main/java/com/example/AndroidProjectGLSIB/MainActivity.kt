package com.example.AndroidProjectGLSIB

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.AndroidProjectGLSIB.db.productsDatabase
import com.example.AndroidProjectGLSIB.model.UserData
import com.malkinfo.editingrecyclerview.R
import com.malkinfo.editingrecyclerview.view.UserAdapter
import java.io.ByteArrayOutputStream

class MainActivity : AppCompatActivity() {
    private lateinit var addsBtn:FloatingActionButton
    private lateinit var recv:RecyclerView
    private lateinit var userList:ArrayList<UserData>
    private lateinit var userAdapter:UserAdapter
    lateinit var imagePost: ImageView
    var bitmap: Bitmap?= null
    private var product: UserData? = null

    lateinit var db : productsDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db= productsDatabase(this);

        /**set List*/
      //  userList = ArrayList()
        userList = db.findProduct()
        /**set find Id*/
        addsBtn = findViewById(R.id.addingBtn)
        recv = findViewById(R.id.mRecycler)
        /**set Adapter*/
        userAdapter = UserAdapter(this,userList)
        /**setRecycler view Adapter*/
        recv.layoutManager = LinearLayoutManager(this)
        recv.adapter = userAdapter

        /**set Dialog*/
        addsBtn.setOnClickListener { addInfo() }
    }

    private fun addInfo() {
        val inflter = LayoutInflater.from(this)
        val v = inflter.inflate(R.layout.add_item,null)
        /**set view*/
        val userName = v.findViewById<EditText>(R.id.userName)
        val userNo = v.findViewById<EditText>(R.id.userNo)
        val userCat = v.findViewById<EditText>(R.id.userCat)
        val userDesc = v.findViewById<EditText>(R.id.userDesc)
        imagePost = v.findViewById<ImageView>(R.id.product_image)

        imagePost.setOnClickListener {
            val intentImg = Intent(Intent.ACTION_GET_CONTENT)
            intentImg.type="image/*"
            startActivityForResult(intentImg, 100)
        }

        val addDialog = AlertDialog.Builder(this)

        addDialog.setView(v)
        addDialog.setPositiveButton("Ok"){
            dialog,_->
            val names = userName.text.toString()
            val number = userNo.text.toString()
            val cat = userCat.text.toString()
            val desc = userDesc.text.toString()

            var imagesBlob: ByteArray = getBytes(bitmap!!)

            val product = UserData(names, number, cat, desc, imagesBlob)

            db.addProduct(product)

            userList.add(UserData("Name: $names","Price : $number","Category:$cat","Describe:$desc",imagesBlob))
            userAdapter.notifyDataSetChanged()
            Toast.makeText(this,"Adding product Information Success",Toast.LENGTH_SHORT).show()

            dialog.dismiss()
        }
        addDialog.setNegativeButton("Cancel"){
            dialog,_->
            dialog.dismiss()
            Toast.makeText(this,"Cancel",Toast.LENGTH_SHORT).show()

        }
        addDialog.create()
        addDialog.show()

       userAdapter.onItemClick = {
           Intent(this, product_details::class.java).also{
               intent.putExtra("Title", userList)
                startActivity(it)
            }
          // productDetails(1)
        }

    }

     /*fun productDetails(position: Int){
        Intent(this,product_details::class.java).also{
            intent.putExtra("Title", userList[position].userName)
            startActivity(it)
        }

        }*/


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode==100 && resultCode== RESULT_OK){
            val uri = data?.data

            val inputStream = contentResolver.openInputStream(uri!!)
            bitmap = BitmapFactory.decodeStream(inputStream)
            imagePost.setImageBitmap(bitmap)
        }
    }
    /**ok now run this */
    fun getBytes(bitmap: Bitmap) : ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0,stream);
        return stream.toByteArray()
    }

}