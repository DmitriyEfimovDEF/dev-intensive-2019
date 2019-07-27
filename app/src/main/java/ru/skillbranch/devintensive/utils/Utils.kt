package ru.skillbranch.devintensive.utils

object Utils {

    fun parseFullName(fullName: String?): Pair<String?, String?> {

        if (fullName.isNullOrBlank())
            return null to null

        val parts: List<String>? = fullName.split(" ")

        var firstName = parts?.getOrNull(0)
        var lastName = parts?.getOrNull(1)

        if (firstName.isNullOrBlank())
            firstName = null

        if (lastName.isNullOrBlank())
            lastName = null

        return firstName to lastName
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        val first = if (firstName.isNullOrBlank()) null else firstName.first().toUpperCase().toString()
        val last = if (lastName.isNullOrBlank()) null else lastName.first().toUpperCase().toString()

        return if (first == null && last == null) null else (first ?: "") + (last ?: "")
    }

    private val tm = mapOf(
        'а' to "a",
        'б' to "b",
        'в' to "v",
        'г' to "g",
        'д' to "d",
        'е' to "e",
        'ё' to "e",
        'ж' to "zh",
        'з' to "z",
        'и' to "i",
        'й' to "i",
        'к' to "k",
        'л' to "l",
        'м' to "m",
        'н' to "n",
        'о' to "o",
        'п' to "p",
        'р' to "r",
        'с' to "s",
        'т' to "t",
        'у' to "u",
        'ф' to "f",
        'х' to "h",
        'ц' to "c",
        'ч' to "ch",
        'ш' to "sh",
        'щ' to "sh'",
        'ъ' to "",
        'ы' to "i",
        'ь' to "",
        'э' to "e",
        'ю' to "yu",
        'я' to "ya"
    )

    fun transliteration(payload: String, divider: String = " ") = buildString {
        payload.forEach {
            append(
                when {
                    it == ' ' -> divider
                    it.isUpperCase() -> tm[it.toLowerCase()]?.capitalize() ?: it.toString()
                    else -> tm[it] ?: it.toString()
                }
            )
        }
    }

    private val exceptOf = setOf(
        "enterprise",
        "features",
        "topics",
        "collections",
        "trending",
        "events",
        "marketplace",
        "pricing",
        "nonprofit",
        "customer-stories",
        "security",
        "login",
        "join")

    fun isRepoValid(repo: String): Boolean {
        if (repo.endsWith("/")) {
            return false
        }

        val httpsStart = repo.startsWith("https://github.com/") || repo.startsWith("https://www.github.com/")
        if (httpsStart) {
            val splitted = repo.split("/").filter { it.isNotEmpty() }

            if (splitted.size != 3)
                return false

            exceptOf.forEach {
                if (splitted[2] == it)
                    return false
            }

            return true

        }

        val wwwStart = repo.startsWith("www.github.com") || repo.startsWith("github.com")

        if (wwwStart) {
            val splitted = repo.split("/").filter { it.isNotEmpty() }

            if (splitted.size != 2)
                return false

            exceptOf.forEach {
                if (splitted[1] == it)
                    return false
            }

            return true
        }

        return false
    }



}

