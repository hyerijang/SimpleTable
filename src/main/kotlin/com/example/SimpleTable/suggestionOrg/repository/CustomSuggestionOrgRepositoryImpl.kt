package com.example.SimpleTable.suggestionOrg.repository

import com.example.SimpleTable.table.SuggestionOrg
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.*
import org.springframework.stereotype.Repository

@Repository
class CustomSuggestionOrgRepositoryImpl(
    @Autowired private val mongoTemplate: MongoTemplate
) : CustomSuggestionOrgRepository {

    override fun findAllByOrderByDisplayOrderAscWithNullsLast(pageable: Pageable): Page<SuggestionOrg> {
        // Create an aggregation to handle null values by replacing them with a high number for sorting
        val addFieldsStage = Aggregation.addFields()
            .addField("sortOrder").withValue(
                ConditionalOperators.ifNull("displayOrder").then(999999)
            ).build()

        val sortStage = Aggregation.sort(
            org.springframework.data.domain.Sort.by(
                org.springframework.data.domain.Sort.Order.asc("sortOrder"),
                org.springframework.data.domain.Sort.Order.desc("createdAt") // 순서가 동일한 경우, 최신데이터가 가장 앞으로
            )
        )
        val skipStage = Aggregation.skip((pageable.pageNumber * pageable.pageSize).toLong())
        val limitStage = Aggregation.limit(pageable.pageSize.toLong())

        val aggregation = Aggregation.newAggregation(addFieldsStage, sortStage, skipStage, limitStage)
        val results: AggregationResults<SuggestionOrg> = mongoTemplate.aggregate(
            aggregation, "suggestionOrg", SuggestionOrg::class.java
        )

        // Aggregation to count total documents
        val countAggregation = Aggregation.newAggregation(
            Aggregation.group().count().`as`("total")
        )
        val countResults = mongoTemplate.aggregate(countAggregation, "suggestionOrg", CountResult::class.java)
        val total = countResults.mappedResults.firstOrNull()?.total ?: 0L

        return PageImpl(results.mappedResults, pageable, total)
    }

    data class CountResult(val total: Long)
}