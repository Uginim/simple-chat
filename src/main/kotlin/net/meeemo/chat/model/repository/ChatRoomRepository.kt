package net.meeemo.chat.model.repository

import net.meeemo.chat.model.entity.chat.ChatRoom
import org.springframework.data.jpa.repository.JpaRepository

interface ChatRoomRepository: JpaRepository<ChatRoom, Long>{

}