package com.example.SimpleTable.table

import com.example.SimpleTable.suggestionOrg.repository.SuggestionOrgMongoRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class SuggestionOrgsService(private val suggestionOrgMongoRepository: SuggestionOrgMongoRepository) {
    fun getAllRecords(): List<SuggestionOrg> = suggestionOrgMongoRepository.findAll()
    fun saveRecord(suggestionOrg: SuggestionOrg): SuggestionOrg {
        return suggestionOrgMongoRepository.save(suggestionOrg)
    }

    fun getRecordsByServiceType(serviceType: String): List<SuggestionOrg> =
        suggestionOrgMongoRepository.findBySrcServiceType(serviceType)

    fun findAllByOrderByDisplayOrderDesc(serviceType: String): List<SuggestionOrg> =
        suggestionOrgMongoRepository.findBySrcServiceTypeOrderByDisplayOrderDesc(serviceType)

    fun getAllByOrderByDisplayOrderDesc(): List<SuggestionOrg> =
        suggestionOrgMongoRepository.findAllByOrderByDisplayOrderDesc()

    fun findAllByOrderByDisplayOrderAscWithNullsLast(pagable: Pageable): Page<SuggestionOrg>
        {
            return suggestionOrgMongoRepository.findAllByOrderByDisplayOrderAscWithNullsLast(pagable)
        }

}


