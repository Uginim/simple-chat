package net.meeemo.chat.model.dto.chat

import net.meeemo.chat.model.entity.user.ChatUser
import net.meeemo.chat.enums.MessageType

data class ChatMessageDTO (
    var content: String,
    var senderName: String,
    var messageType: MessageType
)