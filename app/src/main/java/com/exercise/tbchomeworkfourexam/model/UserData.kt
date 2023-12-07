package com.exercise.tbchomeworkfourexam.model

data class UserData(
    val id: Int,
    val owner: String,
    val image: String ?= null,
    val is_typing: Boolean,
    val last_active: String,
    val last_message: String ?= null,
    val laste_message_type: String ?= null,
    val unread_messages: Int ?= 0,
){
    var initial = Type.TEXT
    init {
        if (laste_message_type == "file"){
            initial = Type.FILE
        }else if (laste_message_type == "voice"){
            initial = Type.VOICE
        }
    }
    enum class Type{
        FILE,
        TEXT,
        VOICE,
    }
}