package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.extensions.humanizeDiff
import java.util.*

class ImageMessage(
    id: String,
    from: User?,
    chat: Chat,
    isIncoming: Boolean = false,
    timeStamp: Date = Date(),
    var image:String?
):BaseMessage(id,from,chat,isIncoming,timeStamp) {
     override fun formatMessage() = "id:$id ${from?.firstName} " +
            "${if (isIncoming)"получил" else "отправил"} " +
            "сообщение \"$image\" ${timeStamp.humanizeDiff()} "
}