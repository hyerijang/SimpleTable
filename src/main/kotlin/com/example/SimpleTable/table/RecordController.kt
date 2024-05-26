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
            recordService.getAllRecords()
        } else {
            recordService.getRecordsByServiceType(serviceType)
        }
    }
    @PostMapping
    fun saveRecords(@RequestBody records: List<Record>): List<Record> {

        return records.map { recordService.saveRecord(it) }

    }
}
