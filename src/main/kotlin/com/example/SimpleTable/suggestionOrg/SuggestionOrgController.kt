package com.example.SimpleTable.table

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
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
            suggestionOrgService.findAllByOrderByDisplayOrderDesc(srcServiceType!!)
        }
    }

    @PostMapping
    fun saveSuggestionOrg(@RequestBody suggestionOrgList: List<SuggestionOrg>): ResponseEntity<List<SuggestionOrg>> {

        val li: List<SuggestionOrg> = suggestionOrgList.map { suggestionOrgService.saveRecord(it) }
        return ResponseEntity(li, HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun updateSuggestionOrg(@PathVariable id: String, @RequestBody suggestionOrg: SuggestionOrg): SuggestionOrg {
        return suggestionOrgService.saveRecord(suggestionOrg)
    }


//    @GetMapping("/null_last")
//    fun nullLast(
//        @RequestParam(required = false, defaultValue = "0") page: Int,
//        @RequestParam(required = false, defaultValue = "10") size: Int
//    ): Page<SuggestionOrg> {
//        val pageable: PageRequest = PageRequest.of(page, size)
//        return suggestionOrgService.nullLast(pageable)
//    }

}
