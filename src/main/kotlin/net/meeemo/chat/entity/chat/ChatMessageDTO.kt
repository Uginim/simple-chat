package net.meeemo.chat.entity.chat

import net.meeemo.chat.entity.user.ChatUser
import net.meeemo.chat.enums.MessageType

data class ChatMessageDTO (
    var content: String,
    var senderName: String,
    var messageType: MessageType
)