package net.meeemo.chat.service

import net.meeemo.chat.entity.chat.ChatMessageDTO
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service

@Service
class ChatMessageService(private val template: SimpMessagingTemplate) {
    fun send(chatRoomCode: String, chatMessageDto: ChatMessageDTO) {
        template.convertAndSend("/chat/messages/$chatRoomCode", chatMessageDto)
//        template.convertAndSend("/chat/messages", chatMessageDto)
    }
}