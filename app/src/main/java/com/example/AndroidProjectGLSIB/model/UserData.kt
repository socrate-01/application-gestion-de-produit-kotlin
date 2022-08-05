package com.example.AndroidProjectGLSIB.model

data class UserData (
    var userName:String,
    var userMb:String,
    var userCat:String,
    var userDesc:String,
    var image : ByteArray
        ){
    var id: Int = -1
    constructor(id:Int, userName: String, userMb: String, userCat: String, userDesc: String, image: ByteArray ) : this(userName, userMb, userCat, userDesc , image) {
        this.id = id
    }
}