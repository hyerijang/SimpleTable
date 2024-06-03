package com.example.SimpleTable.table

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/fruits")
class FriutsController {

    @GetMapping
    fun getName(@RequestParam(required = false) name: String?): String {
        return name ?: "ALL"
    }

}
