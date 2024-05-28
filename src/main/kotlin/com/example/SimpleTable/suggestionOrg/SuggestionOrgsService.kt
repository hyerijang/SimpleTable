package com.example.SimpleTable.table

import org.springframework.stereotype.Service

@Service
class SuggestionOrgsService(private val suggestionOrgRepository: SuggestionOrgRepository) {
    fun getAllRecords(): List<SuggestionOrg> = suggestionOrgRepository.findAll()
    fun saveRecord(suggestionOrg: SuggestionOrg): SuggestionOrg {
        return suggestionOrgRepository.save(suggestionOrg)
    }

    fun getRecordsByServiceType(serviceType: String): List<SuggestionOrg> =
        suggestionOrgRepository.findBySrcServiceType(serviceType)

    fun findAllByOrderByDisplayOrderDesc(serviceType: String): List<SuggestionOrg> =
        suggestionOrgRepository.findBySrcServiceTypeOrderByDisplayOrderDesc(serviceType)

    fun getAllByOrderByDisplayOrderDesc(): List<SuggestionOrg> =
        suggestionOrgRepository.findAllByOrderByDisplayOrderDesc()

}


