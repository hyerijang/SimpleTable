package com.example.SimpleTable

import com.example.SimpleTable.suggestionOrg.repository.SuggestionOrgMongoRepository
import com.example.SimpleTable.table.SuggestionOrg
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ActiveProfiles
import java.io.*
import java.lang.Math.abs
import java.time.LocalDateTime
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals as assertNotEquals

@DataMongoTest
@ActiveProfiles("test")
@ComponentScan(basePackages = ["com.example.SimpleTable.suggestionOrg.repository"])
class SuggestionOrgMongoRepositoryTest {

    @Autowired
    lateinit var repository: SuggestionOrgMongoRepository


    @Test
    fun init() {

        repository.deleteAll()
        val random = Random()

        for (i in 1..100) {
            val randomId = abs(random.nextLong() % 10)
            val suggestionOrg = SuggestionOrg(null, "Type" + (i % 10), LocalDateTime.now(), LocalDateTime.now(),randomId)
            val suggestionOrg2 = SuggestionOrg(null, "Type" + (i % 10), LocalDateTime.now(), LocalDateTime.now(), null)
            repository.save(suggestionOrg)
            repository.save(suggestionOrg2)
        }
    }


    // TODO : API 에서 어떤 방법을 쓰는게 나을까?
    @Test
    fun test1() {
        // 이게 기존 방식에 가까운듯.
        // 모든 정렬과 필터링을 서버쪽에서 한다.

        //1, 전체 데이터 가져오기
        val all = repository.findAll()
        val (resultNoDisplayOrder, resultWithDisplayOrder) = all.partition { it.displayOrder == null }

        //2. 순서 있/ 없 각각 정렬 후 합친다.
        resultWithDisplayOrder.sortedWith(compareBy({ it.displayOrder }, { it.id }))
        resultNoDisplayOrder.sortedBy { it.id }
        val allData = resultWithDisplayOrder + resultNoDisplayOrder

        // 2. 서비스 유형별로 필터링
        val filteredResult = allData.filter { it.srcServiceType == "Type1" }
//        println(filteredResult)


    }


    @Test
    fun test2() {
        // 흠... 성능 개선을 딱히 없을듯.
        // 순서 잇없 따로 가져오는데... 음? 그냥 풀 스캔이 두번인거 아냐 ? 1보다 비효율적이네

        //1, 전체 데이터 가져온 후 정렬
        val resultWithDisplayOrder = repository.findAllByDisplayOrderIsNotNull()
            .sortedWith(compareBy({ it.displayOrder }, { it.id }))

        val resultNoDisplayOrder = repository.findAllByDisplayOrderIsNull()
            .sortedBy { it.id }

        // 2. 합치기
        val allData = resultWithDisplayOrder + resultNoDisplayOrder

        // 2. 서비스 유형별로 필터링
        val filteredResult = allData.filter { it.srcServiceType == "Type1" }

    }


    @Test
    fun test3() {
        // 얘가 성능은 제일 좋네

        //1, 서비스 유형별로 데이터 가져오기 (DB에서 필터링)
        // 인덱스 없는 필드 기준으로 필터링하느라 결국 full 스캔을 하긴 할건데... DB에서 가져오는 네트워크량이 좀 줄겠지?
        val filteredResult = repository.findBySrcServiceType("Type1")

        //2. displaaOrder 값 기준 null / null아님 으로 구분
        val (resultNoDisplayOrder, resultWithDisplayOrder) = filteredResult.partition { it.displayOrder == null }

        //3. 각각 정렬
        val a  =  resultWithDisplayOrder.sortedWith(compareBy({ it.displayOrder }, { it.id }))
        val b = resultNoDisplayOrder.sortedBy { it.id }


//        println(resultWithDisplayOrder)
//        println("-------------------------")
//        println(resultNoDisplayOrder)

    }


    @DisplayName("정렬 전 후 값이 달라야 한다.")
    @Test
    fun test4() {

        val filteredResult = repository.findBySrcServiceType("Type1")
        println(filteredResult.get(0))

        val sortedResult = filteredResult.sortedBy { it.displayOrder } // 달라야함
        println(sortedResult.get(0))

        assertNotEquals(
            filteredResult,
            sortedResult
        )

    }


    @DisplayName("id 순으로 정렬하면 정렬 전 후 값이 같아야 한다.")
    @Test
    fun idSortTest() {

        val filteredResult = repository.findBySrcServiceType("Type1")

        //2. displaaOrder 값 기준 null / null아님 으로 구분
        val resultWithDisplayOrder = filteredResult.filter { it.displayOrder != null }

        //3. 각각 정렬
        val b = resultWithDisplayOrder.sortedBy { it.id }

        assertEquals(
            resultWithDisplayOrder,
            b
        )

    }

    @DisplayName("displayOrder순으로 정렬하면 정렬 전 후 값이 달라야한다 한다.")
    @Test
    fun doSortTest() {

        val filteredResult = repository.findBySrcServiceType("Type1")

        //2. displaaOrder 값 기준 null / null아님 으로 구분
        val resultWithDisplayOrder = filteredResult.filter { it.displayOrder != null }

        //3. 각각 정렬
        val c = resultWithDisplayOrder.sortedBy { it.displayOrder }

        assertNotEquals(
            resultWithDisplayOrder,
            c
        )
    }

    @DisplayName("id 정렬과 createdAt 정렬은 동일한 결과가 나와야한다.")
    @Test
    fun idcreated() {

        val filteredResult = repository.findBySrcServiceType("Type1")

        //2. displaaOrder 값 기준 null / null아님 으로 구분
        val resultWithDisplayOrder = filteredResult.filter { it.displayOrder != null }

        //3. 각각 정렬
        val i = resultWithDisplayOrder.sortedBy { it.id }
        val c = resultWithDisplayOrder.sortedBy { it.createdAt}

        assertEquals(
            i,
            c
        )
    }



}