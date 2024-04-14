package com.example.supremedollop

import com.example.supremedollop.repository.FakeSalesmanRepository
import com.example.supremedollop.repository.SalesmanRepository
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

class FakeRepositoryTest {
    private lateinit var repository: SalesmanRepository

    @Before
    fun setup() {
        repository = FakeSalesmanRepository()
    }

    @Test
    fun `findSalesmenByArea with exact match returns correct salesmen`() {
        // Given
        val areaQuery = "76133"

        // When
        val result = repository.findSalesmenByArea(areaQuery)

        // Then
        assertEquals(1, result.size)
        assertEquals("Artem Titarenko", result.first().name)
    }

    @Test
    fun `findSalesmenByArea without one digit match returns correct salesman`() {
        // Given
        val areaQuery = "7619"

        // When
        val result = repository.findSalesmenByArea(areaQuery)

        // Then
        assertTrue(result.any { it.name == "Bernd Schmitt" })
    }

    @Test
    fun `findSalesmenByArea with no match returns empty list`() {
        // Given
        val areaQuery = "99999"

        // When
        val result = repository.findSalesmenByArea(areaQuery)

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `findSalesmenByArea matches all valid postcodes within wildcard range with a salesman`() {
        // Given
        val validPostcodes = (76200..76299).map { it.toString() }

        // When
        validPostcodes.forEach { postcode ->
            val result = repository.findSalesmenByArea(postcode)

            // Then
            assertTrue("Salesman with area '762*' should be returned for postcode $postcode",
                result.any { it.name == "Chris Krapp" })
        }
    }

    @Test
    fun `findSalesmenByArea matches query with more than one salesman`() {
        // Given
        val wildcardQuery = "761"

        // When
        val results = repository.findSalesmenByArea(wildcardQuery)

        // Then
        assertTrue("Salesman Bernd Schmitt should be returned", results.any { it.name == "Bernd Schmitt" })
        assertTrue("Salesman Artem Titarenko should be returned", results.any { it.name == "Artem Titarenko" })
        assertTrue("Salesman Anna Muller should be returned", results.any { it.name == "Anna Muller" })
        assertEquals("Exactly three salesmen should match '762'", 3, results.size)
    }

    @Test
    fun `findSalesmenByArea returns same salesman who has 2 postcodes`() {
        // Given
        val wildcardQuery1 = "73133"
        val wildcardQuery2 = "76131"

        // When
        val results1 = repository.findSalesmenByArea(wildcardQuery1)
        val results2 = repository.findSalesmenByArea(wildcardQuery2)

        // Then
        assertTrue("For the first query Anna Muller should be returned", results1.any { it.name == "Anna Muller" })
        assertTrue("For the second query Anna Muller should be returned", results2.any { it.name == "Anna Muller" })
    }
}