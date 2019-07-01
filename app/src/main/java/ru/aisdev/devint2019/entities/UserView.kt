package ru.aisdev.devint2019.entities

import ru.aisdev.devint2019.utils.Utils

class UserView(
    val id: String,
    val fullName: String,
    val nickName: String,
    var avatar: String? = null,
    var status: String? = "offline",
    var initials:String? = null
) {

    fun printMe() {
        println("""
    id: $id: 
    fullName: $fullName: 
    nickName: $nickName: 
    avatar:  $avatar:  
    status: $status: 
    initials: $initials: 
        """.trimIndent())
    }
}