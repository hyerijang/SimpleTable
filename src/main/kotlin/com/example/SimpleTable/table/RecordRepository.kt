package com.example.SimpleTable.table

import org.springframework.data.mongodb.repository.MongoRepository

interface RecordRepository : MongoRepository<Record, String>{
    fun findByServiceType(serviceType: String): List<Record>
}


