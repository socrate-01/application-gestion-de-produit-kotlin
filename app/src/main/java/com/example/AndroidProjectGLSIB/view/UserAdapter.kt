package com.malkinfo.editingrecyclerview.view

import android.app.AlertDialog
import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.malkinfo.editingrecyclerview.R
import com.example.AndroidProjectGLSIB.db.productsDatabase
import com.example.AndroidProjectGLSIB.model.UserData

class UserAdapter(val c:Context,val userList:ArrayList<UserData>):RecyclerView.Adapter<UserAdapter.UserViewHolder>()
{
    var bitmap: Bitmap?= null
    lateinit var image: ImageView

    var onItemClick : ((UserData)-> Unit)? = null
    var onDeleteItemClick : ((UserData)-> Unit)? = null

    fun setonDeleteItemClick(callback:(UserData)->Unit)
    {
        this.onDeleteItemClick = callback
    }


    inner class UserViewHolder(val v:View):RecyclerView.ViewHolder(v){
      var name:TextView
      var mbNum:TextView

      var mMenus:ImageView
      lateinit var category:TextView
      lateinit var description:TextView

      private var product: UserData? = null
      lateinit var db : productsDatabase

      init {
          name = v.findViewById<TextView>(R.id.mTitle)
          mbNum = v.findViewById<TextView>(R.id.mSubTitle)
          image = v.findViewById(R.id.mImage)
          mMenus = v.findViewById(R.id.mMenus)
          mMenus.setOnClickListener { popupMenus(it) }

         /* itemView.setOnClickListener{
              val position : Int = adapterPosition
              Toast.makeText(v.context,"vous avez cliqué sur le produit ${userList[position]}",Toast.LENGTH_LONG).show()
          }*/

      }


     /* private fun updateProduct(){
          val name = name.text.toString()
          val num = mbNum.text.toString()
          val category = category.text.toString()
          val description = description.text.toString()
          val image = image.setImageBitmap(bitmap)

        /*  if((name == product?.userName) && (num == product?.userMb) && (category == product?.userCat) && (description == product?.userDesc) && (image.equals(product?.image))) {
            Toast.makeText(this, "Record not changed", Toast.LENGTH_SHORT).show()
              return
          }*/
          if(product == null) return

          val product = UserData(id = product!!.id, name = name, num = num, category = category, description = description, image = image)
          val status = db.updateProduct(product)
          if(status > -1){
              clearEditText()
          }
      }*/

      private fun clearEditText(){
          name.setText("")
          mbNum.setText("")
          category.setText("")
          description.setText("")
          image.setImageBitmap(null)
      }

       /* private fun deleteProduct(id:Int){
            db.deleteProduct(id)
        }*/




       private fun popupMenus(v:View) {
          val position = userList[adapterPosition]
          val popupMenus = PopupMenu(c,v)
          popupMenus.inflate(R.menu.show_menu)
          popupMenus.setOnMenuItemClickListener {
              when(it.itemId){
                  R.id.editText->{
                      val v = LayoutInflater.from(c).inflate(R.layout.add_item,null)
                      val name = v.findViewById<EditText>(R.id.userName)
                      val number = v.findViewById<EditText>(R.id.userNo)
                      val cat = v.findViewById<EditText>(R.id.userCat)
                      val desc = v.findViewById<EditText>(R.id.userDesc)

                              AlertDialog.Builder(c)
                                      .setView(v)
                                      .setPositiveButton("Ok"){
                                          dialog,_->
                                          position.userName = name.text.toString()
                                          position.userMb = number.text.toString()
                                          position.userCat = cat.text.toString()
                                          position.userDesc = desc.text.toString()

                                          notifyDataSetChanged()
                                          Toast.makeText(c,"Product Information is Edited",Toast.LENGTH_SHORT).show()
                                          //updateProduct()
                                          dialog.dismiss()

                                      }
                                      .setNegativeButton("Cancel"){
                                          dialog,_->
                                          dialog.dismiss()

                                      }
                                      .create()
                                      .show()

                      true
                  }
                  R.id.delete->{
                      /**set delete*/
                      AlertDialog.Builder(c)
                              .setTitle("Delete")
                              .setIcon(R.drawable.ic_warning)
                              .setMessage("Are you sure delete this Product")
                              .setPositiveButton("Yes"){
                                  dialog,_->
                                   //db.deleteProduct(id)
                                  userList.removeAt(adapterPosition)
                                  notifyDataSetChanged()
                                  Toast.makeText(c,"Deleted this Product",Toast.LENGTH_SHORT).show()
                                  dialog.dismiss()
                              }
                              .setNegativeButton("No"){
                                  dialog,_->
                                  dialog.dismiss()
                              }
                              .create()
                              .show()

                      true
                  }
                  else-> true
              }

          }
          popupMenus.show()
          val popup = PopupMenu::class.java.getDeclaredField("mPopup")
          popup.isAccessible = true
          val menu = popup.get(popupMenus)
          menu.javaClass.getDeclaredMethod("setForceShowIcon",Boolean::class.java)
                  .invoke(menu,true)
      }
  }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
       val inflater = LayoutInflater.from(parent.context)
        val v  = inflater.inflate(R.layout.list_item,parent,false)
        return UserViewHolder(v)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
       val newList = userList[position]
        holder.name.text = newList.userName
        holder.mbNum.text = newList.userMb
        val bitmap = getBitmap(newList.image)
        image.setImageBitmap(bitmap)
        holder.itemView.setOnClickListener{
            onItemClick?.invoke(newList)
        }

        //holder.bindView()

        //holder.image.setImageBitmap(bitmap) = newList.image
        /*holder.cat.text = newList.userCat
        holder.desc.text = newList.userDesc*/
    }


    override fun getItemCount(): Int {
      return  userList.size
    }

    fun getBitmap(byteArray: ByteArray) : Bitmap {
        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        return bitmap
    }

}


