package com.example.SimpleTable.suggestionOrg.repository

import com.example.SimpleTable.table.SuggestionOrg
import org.springframework.data.mongodb.repository.MongoRepository

interface SuggestionOrgMongoRepository : MongoRepository<SuggestionOrg, String>, CustomSuggestionOrgRepository {
    fun findBySrcServiceType(srcServiceType: String): List<SuggestionOrg>
    fun findBySrcServiceTypeOrderByDisplayOrderDesc(srcServiceType: String): List<SuggestionOrg>
    fun findAllByOrderByDisplayOrderDesc(): List<SuggestionOrg>

}

