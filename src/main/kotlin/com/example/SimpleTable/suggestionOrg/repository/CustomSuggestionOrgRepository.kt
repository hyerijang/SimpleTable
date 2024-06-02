package com.example.SimpleTable.suggestionOrg.repository

import com.example.SimpleTable.table.SuggestionOrg
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CustomSuggestionOrgRepository {
    fun findAllByOrderByDisplayOrderAscWithNullsLast(pageable: Pageable): Page<SuggestionOrg>
}