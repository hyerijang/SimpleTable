package com.example.SimpleTable.table

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface RecordRepository : MongoRepository<Record, String>{
    fun findByServiceType(serviceType: String): List<Record>


    @Query("{'serviceType': ?0}, {'sortOrder': 1}")
    fun findByServiceTypeOrderBySortOrderAsc(serviceType: String): List<Record>

    @Query("{}, {'sortOrder': 1}")
    fun findAllOrderBySortOrderAsc(): List<Record>
}


