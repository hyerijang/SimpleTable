package com.example.SimpleTable.table

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "records")
data class SuggestionOrg(
    @Id val id: String? = null,
    val srcServiceType: String?,
    val createdAt: LocalDateTime? = LocalDateTime.now(),
    val updatedAt: LocalDateTime? = LocalDateTime.now(),
    var displayOrder: Long? = 0,
)
