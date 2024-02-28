package net.meeemo.chat.model.repository

import net.meeemo.chat.model.entity.chat.ChatRoom
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ChatRoomRepositoryTest(
    @Autowired
    private val chatRoomRepository: ChatRoomRepository
) {

//    private val logger = LoggerFactory.getLogger(javaClass)
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Test
    fun testChatRoomCreating () {
        // given

        val randomValue = (Math.random() * 1000000).toInt()
        val newChatRoom = ChatRoom(
            name = "test",
            code = String.format("%06d", randomValue)
        )
        // when
        chatRoomRepository.save(newChatRoom)
        // then
        val savedChatRoom = chatRoomRepository
            .findById(newChatRoom.id!!)
            .orElseThrow { NoSuchElementException() }

        assert(savedChatRoom.name == newChatRoom.name)
        logger.info("savedChatRoom Id: ${savedChatRoom.id}")
    }
}