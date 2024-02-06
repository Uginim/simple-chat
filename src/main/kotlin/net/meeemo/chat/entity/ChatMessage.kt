package net.meeemo.chat.entity

import net.meeemo.chat.enums.MessageType

data class ChatMessage (
    var content: String? = null,
    var sender: String? = null,
    var type: MessageType? = null
)