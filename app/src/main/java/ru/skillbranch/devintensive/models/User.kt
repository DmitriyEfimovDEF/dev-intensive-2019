package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.utils.Utils
import java.util.*

data class User(
    val id : String,
    var firstName : String?,
    var lastName : String?,
    var avatar : String?,
    var rating : Int = 0,
    var respect : Int = 0,
    var lastVisit : Date? = Date(),
    var isOnline : Boolean = false
) {
    companion object {
        private var lastId = -1
        fun makeUser(fullName: String?): User {
            lastId++


            val (firstName,lastName) = Utils.parseFullName(fullName)
            return User(id = "$lastId",
            firstName = firstName,
            lastName = lastName)
        }
    }

    var introBit: String

    constructor(id:String,firstName: String?,lastName: String?):this(
        id = id,
        firstName = firstName,
        lastName = lastName,
        avatar = null
    )
    constructor(id: String):this(id,"John","Doe $id")
    init {
        introBit = getIntro()
        println("it's alive!\n" +
                " ${if (lastName?.let { it == "Doe" }!!) "It's name is $firstName $lastName" else " And his name  is $lastName $firstName" }\n" +
                getIntro()
        )
    }

    private fun getIntro() = """
                ${"\n"}
        life is hard 
        that's why no one survives
    """.trimIndent()


    fun printMe() = println("""
            id - $id
            firstName  - $firstName
            lastName  - $lastName
            avatar  - $avatar
            rating  - $rating
            respect  - $respect
            lastVisit  - $lastVisit
            isOnline - $isOnline
        """.trimIndent())

    data class Builder(
        private var id: String = "",
        private var firstName: String? = null,
        private var lastName: String? = null,
        private var avatar: String? = null,
        private var rating: Int = 0,
        private var respect: Int = 0,
        private var lastVisit: Date? = Date(),
        private var isOnline: Boolean = false
    ) {
        fun id(id: String) = apply {this.id = id}
        fun firstName(firstName: String) = apply {this.firstName = firstName}
        fun lastName(lastName: String) = apply {this.lastName = lastName}
        fun avatar(avatar: String) = apply {this.avatar = avatar}
        fun rating(rating: Int) = apply {this.rating = rating}
        fun respect(respect: Int) = apply {this.respect = respect}
        fun lastVisit(lastVisit: Date) = apply {this.lastVisit = lastVisit}
        fun isOnline(isOnline: Boolean) = apply {this.isOnline = isOnline}
        fun build() = User(id, firstName,lastName,avatar, rating, respect, lastVisit, isOnline)
    }

}