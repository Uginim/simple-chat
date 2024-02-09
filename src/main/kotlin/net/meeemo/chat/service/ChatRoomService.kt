package net.meeemo.chat.service

import org.springframework.stereotype.Service

@Service
class ChatRoomService {

    // 해시 값 6자리 랜덤 생성
    fun createChatRoomCode(): String{

        val randomValue = (Math.random() * 1000000).toInt()
        return String.format("%06d", randomValue)
    }
}