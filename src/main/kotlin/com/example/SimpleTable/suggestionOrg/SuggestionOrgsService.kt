package com.example.SimpleTable.table

import com.example.SimpleTable.suggestionOrg.repository.SuggestionOrgMongoRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class SuggestionOrgsService(private val suggestionOrgMongoRepository: SuggestionOrgMongoRepository) {
    fun getAllRecords(): List<SuggestionOrg> = suggestionOrgMongoRepository.findAll()


    fun save(suggestionOrg: SuggestionOrg): SuggestionOrg {
        return suggestionOrgMongoRepository.save(suggestionOrg)
    }

    fun saveAll(suggestionOrgs: List<SuggestionOrg>): List<SuggestionOrg> {
        suggestionOrgs.forEach { suggestionOrg ->
            val duplicates = suggestionOrgMongoRepository.findByOrgCodeAndSrcServiceType(
                suggestionOrg.orgCode, suggestionOrg.srcServiceType
            )
            if (duplicates.isNotEmpty()) {
                throw DuplicateSuggestionOrgException("서비스 유형이 ${suggestionOrg.srcServiceType}인 기관 ${suggestionOrg.orgName}(${suggestionOrg.orgCode})이 이미 존재합니다.")
            }
        }


        return suggestionOrgMongoRepository.saveAll(suggestionOrgs)
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

// Custom exception
class DuplicateSuggestionOrgException(message: String) : RuntimeException(message)
