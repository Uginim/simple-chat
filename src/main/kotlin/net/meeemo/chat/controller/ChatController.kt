package net.meeemo.chat.controller

import net.meeemo.chat.entity.ChatMessage
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller

@Controller
class ChatController {

    @MessageMapping("/send")
    @SendTo("/chat/messages")
    fun send(chatMessage: ChatMessage): ChatMessage {
        return chatMessage
    }
}