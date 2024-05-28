package com.example.SimpleTable.table

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/suggestion_orgs")
class SuggestionOrgController(private val suggestionOrgService: SuggestionOrgsService) {


    @GetMapping
    fun getAllRecords(@RequestParam(required = false) srcServiceType: String?): List<SuggestionOrg> {
        return if (srcServiceType.isNullOrEmpty()) {
            suggestionOrgService.getAllByOrderByDisplayOrderDesc()
        } else {
            suggestionOrgService.findAllByOrderByDisplayOrderDesc(srcServiceType)
        }
    }

    @PostMapping
    fun saveRecords(@RequestBody suggestionOrgList: List<SuggestionOrg>): ResponseEntity<List<SuggestionOrg>> {

        val li: List<SuggestionOrg> = suggestionOrgList.map { suggestionOrgService.saveRecord(it) }
        return ResponseEntity(li,HttpStatus.CREATED)
//        return ResponseEntity(HttpStatus.BAD_REQUEST)

    }

    @PutMapping("/{id}")
    fun updateRecord(@PathVariable id: String, @RequestBody suggestionOrg: SuggestionOrg): SuggestionOrg {
        require(id == suggestionOrg.id) { "ID in path must match ID in request body" }
        return suggestionOrgService.saveRecord(suggestionOrg)
    }
}
