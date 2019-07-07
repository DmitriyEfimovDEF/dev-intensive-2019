package ru.skillbranch.devintensive.models

import java.util.*

abstract class BaseMessage(
    val id: String,
    val from: User?,
    val chat: Chat,
    val isIncoming: Boolean = false,
    val timeStamp: Date = Date()
){
    abstract fun formatMessage(): String

    companion object AbstractFactory {
        var lastId = -1
        fun makeMessage(from:User?,chat: Chat,timeStamp: Date = Date(),type:String = "text",payload:Any?): BaseMessage {
            lastId++
            return when(type) {
                "image" ->ImageMessage("$lastId",
                                                        from,
                                                        chat,
                                                        timeStamp = timeStamp,
                                                        image = payload as String,
                                                        isIncoming = false)
                else -> TextMessage("$lastId",
                                                        from,
                                                        chat,
                                                        timeStamp = timeStamp,
                                                        text = payload as String,
                                                        isIncoming = false)


                }
            }
        }
    }



enum class MessageType{
    TEXT,
    IMAGE
}