package com.example.SimpleTable.table

import org.springframework.data.mongodb.repository.MongoRepository

interface SuggestionOrgRepository : MongoRepository<SuggestionOrg, String> {
    fun findBySrcServiceType(srcServiceType: String): List<SuggestionOrg>
    fun findBySrcServiceTypeOrderByDisplayOrderDesc(srcServiceType: String): List<SuggestionOrg>
    fun findAllByOrderByDisplayOrderDesc(): List<SuggestionOrg>
}

