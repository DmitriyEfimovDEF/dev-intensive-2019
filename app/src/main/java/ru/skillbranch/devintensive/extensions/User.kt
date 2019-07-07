package ru.skillbranch.devintensive.extensions


import ru.skillbranch.devintensive.models.User
import ru.skillbranch.devintensive.models.UserView
import ru.skillbranch.devintensive.utils.Utils

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

