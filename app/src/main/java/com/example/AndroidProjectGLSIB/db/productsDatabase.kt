package com.example.AndroidProjectGLSIB.db;

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.AndroidProjectGLSIB.model.UserData
import java.util.ArrayList

public class productsDatabase (mContext : Context) : SQLiteOpenHelper(
    mContext,
    DB_NAME,
    null,
    DB_VERSION,
) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableProduct = """
            CREATE TABLE product(
            $PRODUCT_ID integer PRIMARY KEY,
            $IMAGE blob,
            $NAME varchar(20),
            $PRICE varchar(20),
            $CATEGORY varchar(20),
            $DESCRIPTION varchar(100)
            
            )
        """.trimIndent()
        db?.execSQL(createTableProduct)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $PRODUCTS_TABLE_NAME")
        onCreate(db)
    }

    fun addProduct(product: UserData) : Boolean {
        val db = writableDatabase;
        val values = ContentValues();
        values.put(NAME,product.userName);
        values.put(PRICE,product.userMb);
        values.put(CATEGORY,product.userCat);
        values.put(DESCRIPTION,product.userDesc);
        values.put(IMAGE,product.image);



        val results = db.insert(PRODUCTS_TABLE_NAME,null,values).toInt()
        db.close()
        return results !=-1
    }



    fun findProduct(): ArrayList<UserData>{
        val products = ArrayList<UserData>()
        val db = readableDatabase
        val selectQuery = "SELECT * FROM $PRODUCTS_TABLE_NAME"

        val cursor = db.rawQuery(selectQuery, null)
        if(cursor!=null){
            if(cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow(PRODUCT_ID))
                    val name = cursor.getString(cursor.getColumnIndexOrThrow(NAME))
                    val price = cursor.getString(cursor.getColumnIndexOrThrow(PRICE))
                    val category = cursor.getString(cursor.getColumnIndexOrThrow(CATEGORY))
                    val description = cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION))
                    val image = cursor.getBlob(cursor.getColumnIndexOrThrow(IMAGE))
                    val product = UserData(id, name, price, category, description, image)
                    products.add(product)
                } while (cursor.moveToNext())
            }
        }
        db.close()
        return products
    }

    fun updateProduct(product: UserData) : Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(PRODUCT_ID,product.id)
        contentValues.put(NAME,product.userName)
        contentValues.put(PRICE,product.userMb)
        contentValues.put(CATEGORY,product.userCat)
        contentValues.put(DESCRIPTION,product.userDesc)
        contentValues.put(IMAGE,product.image)

        val sucess = db.update(PRODUCTS_TABLE_NAME,contentValues,"id"+product.id, null)
        db.close()
        return sucess;

    }

    fun deleteProduct(id: Int): Int {
        val db =  this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(PRODUCT_ID,id)

        val success = db.delete(PRODUCTS_TABLE_NAME, "id=$id", null);
        db.close()
        return success
    }

    companion object{
        private val DB_NAME="Product_db"
        private val DB_VERSION = 1
        private val PRODUCTS_TABLE_NAME="product"
        private val PRODUCT_ID="id"
        private val NAME="userName"
        private val PRICE="userMb"
        private val DESCRIPTION="userDesc"
        private val IMAGE="image"
        private val CATEGORY="UserCat"

    }
}

