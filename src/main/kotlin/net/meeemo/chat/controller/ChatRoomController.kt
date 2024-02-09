package net.meeemo.chat.controller

import net.meeemo.chat.service.ChatRoomService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/chat/room")
class ChatRoomController(
    private val chatRoomService: ChatRoomService
) {

    /**
     * 채팅방 생성
     */
    @RequestMapping("/create")
    fun createChatRoom(): String {
        return chatRoomService.createChatRoomCode()
    }

}