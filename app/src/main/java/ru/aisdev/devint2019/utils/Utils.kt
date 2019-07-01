package ru.aisdev.devint2019.utils

object Utils {
    fun parseFullName(fullName:String?): Pair<String?, String?> {
//        println("${fullName?.trimStart()?.trimEnd()?.split(" ")}---")
        val parts = fullName?.trimStart()?.trimEnd()?.split(" ")
        var firstName = "null"
        var lastName=  "null"

        firstName = parts?.get(0)?.let{
            (if(parts[0] =="" || parts[0] ==" ") "null" else parts[0])
        }.toString()
        if(parts?.size==2) lastName = parts[1].let{
            (if(parts[1] =="" || parts[1] ==" ") "null" else parts[1])
        }.toString()

        return Pair(firstName,lastName)
    }
    fun getInitials(fullName: String): String {
        val pair=parseFullName(fullName)
        val first = if (pair.first=="null") "null" else pair.first?.subSequence(0,1).toString().toUpperCase()
        val second =if (pair.second!="null") pair.second?.subSequence(0,1).toString().toUpperCase() else ""
        return first+second
    }

    fun transliteration(payload: String,divider: String =" "): String {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return " "
    }
}