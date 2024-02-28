package net.meeemo.chat.controller

import net.meeemo.chat.model.dto.chat.ChatMessageDTO
import net.meeemo.chat.service.ChatMessageService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller

@Controller
class ChatMessageController(
    private val chatMessageService: ChatMessageService,
) {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @MessageMapping("/send/message/{chatRoomCode}")
    fun send(
        @DestinationVariable chatRoomCode: String,
        chatMessage: ChatMessageDTO,
    ) {
        logger.info("chatRoomCode: $chatRoomCode")
        chatMessageService.send(chatRoomCode, chatMessage)
    }
}