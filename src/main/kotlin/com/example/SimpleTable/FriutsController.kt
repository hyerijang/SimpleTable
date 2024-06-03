package com.example.SimpleTable.table

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/fruits")
class FruitsController {

    private val logger: Logger = LoggerFactory.getLogger(FruitsController::class.java)

    @GetMapping
    fun getAll(@RequestParam(required = false) name: String?): List<Fruit> {
        val fruits = listOf(
            Fruit(1, "apple" , 123),
            Fruit(2, "banana", 111)
        )
        return if (name.isNullOrEmpty()) {
            fruits
        } else {
            fruits.filter { it.name.contains(name, ignoreCase = true) }
        }
    }

    @GetMapping("/{name}")
    fun getName(@PathVariable name: String): Fruit {
        logger.info("선택된 과일의 이름 : {}", name)
        return Fruit((Math.random() * 1000).toLong(), name, 1000)
    }
}

data class Fruit(
    val id: Long,
    val name: String,
    val price: Long?
)
