package com.example.chatapp.model

data class MessageModel (
    var message:String?="",
    var senderId:String?="",
    var timeStamp:Long?=0
        )