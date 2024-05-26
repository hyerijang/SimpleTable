package com.example.SimpleTable.table

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

inline fun <reified T> T.logger() = LoggerFactory.getLogger(T::class.java)!!

@RestController
@RequestMapping("/api/records")
class RecordController(private val recordService: RecordService) {

    val log = logger()


    @GetMapping
    fun getAllRecords(@RequestParam(required = false) serviceType: String?): List<Record> {
        return if (serviceType.isNullOrEmpty()) {
            recordService.getAllRecordsOrderBySortOrderAsc()
        } else {
            recordService.findByServiceTypeOrderBySortOrderAsc(serviceType)
        }
    }
    @PostMapping
    fun saveRecords(@RequestBody records: List<Record>): List<Record> {

        return records.map { recordService.saveRecord(it) }

    }

    @PutMapping("/{id}")
    fun updateRecord(@PathVariable id: String, @RequestBody record: Record): Record {
        require(id == record.id) { "ID in path must match ID in request body" }
        return recordService.saveRecord(record)
    }
}
