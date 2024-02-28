package net.meeemo.chat.model.entity.chat

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import net.meeemo.chat.model.entity.base.BaseEntity

@Entity
class ChatRoom (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var name: String = "",
    val code: String = ""
): BaseEntity() {
    constructor() : this(null, "", "")
}