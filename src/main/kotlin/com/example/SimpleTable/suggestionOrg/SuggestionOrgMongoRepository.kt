package com.example.SimpleTable.table

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository

interface SuggestionOrgMongoRepository : MongoRepository<SuggestionOrg, String> {
    fun findBySrcServiceType(srcServiceType: String): List<SuggestionOrg>
    fun findBySrcServiceTypeOrderByDisplayOrderDesc(srcServiceType: String): List<SuggestionOrg>
    fun findAllByOrderByDisplayOrderDesc(): List<SuggestionOrg>

}

