package ru.aisdev.devint2019.extensions

import ru.aisdev.devint2019.entities.User
import ru.aisdev.devint2019.entities.UserView
import ru.aisdev.devint2019.utils.Utils

fun User.toUserView(): UserView {
    val fullName = "$firstName $lastName"
    val nickName = Utils.transliteration(fullName)
    val initials =
        Utils.getInitials(Utils.parseFullName("$firstName $lastName").toString())
    val status = if (lastVisit == null) "Haven't been there yet" else if (isOnline) "online" else "Last time been there ${lastVisit?.let { it.humanizeDiff() }}"
    
    return UserView(
        id,
        fullName = "$firstName $lastName",
        nickName = nickName,
        initials = initials,
        avatar = avatar,
        status = status
    )
}

