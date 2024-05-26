package com.example.SimpleTable.table
import org.springframework.stereotype.Service

@Service
class RecordService(private val recordRepository: RecordRepository) {
    fun getAllRecords(): List<Record> = recordRepository.findAll()
    fun saveRecord(record: Record): Record = recordRepository.save(record)
    fun getRecordsByServiceType(serviceType: String): List<Record> = recordRepository.findByServiceType(serviceType)
    fun findByServiceTypeOrderBySortOrderAsc(serviceType: String): List<Record> =
        recordRepository.findByServiceTypeOrderBySortOrderAsc(serviceType)

    fun getAllRecordsOrderBySortOrderAsc(): List<Record> = recordRepository.findAllOrderBySortOrderAsc()

}


