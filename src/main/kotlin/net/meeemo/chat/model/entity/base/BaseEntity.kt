package net.meeemo.chat.model.entity.base

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp

@MappedSuperclass
open class BaseEntity {

    var createdBy: String? = null
    var updatedBy: String? = null

    @Column(nullable = false)
    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var createdAt: Long? = null
    @Column
    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var updatedAt: Long? = null
}