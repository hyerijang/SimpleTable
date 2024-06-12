package com.example.SimpleTable.table

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "suggestionOrg")
data class SuggestionOrg(
    @Id val id: String? = null,
    val orgName : String?,
    val orgCode : String?,
    val srcServiceType: String?,
    val createdAt: LocalDateTime? = LocalDateTime.now(),
    val updatedAt: LocalDateTime? = LocalDateTime.now(),
    var displayOrder: Long? = null,
)
