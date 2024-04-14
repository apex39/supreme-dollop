package com.example.supremedollop.repository

import com.example.supremedollop.model.Salesman

interface SalesmanRepository {
    fun getAllSalesmen(): List<Salesman>
    fun findSalesmenByArea(areaQuery: String): List<Salesman>
}

class FakeSalesmanRepository : SalesmanRepository {

    private val salesmen = listOf(
        Salesman("Artem Titarenko", listOf("76133")),
        Salesman("Bernd Schmitt", listOf("7619*")),
        Salesman("Chris Krapp", listOf("762*")),
        Salesman("Alex Uber", listOf("86*")),
        Salesman("Anna Muller", listOf("73133", "76131"))
    )

    override fun getAllSalesmen(): List<Salesman> {
        return salesmen
    }

    override fun findSalesmenByArea(areaQuery: String): List<Salesman> {
        return salesmen.filter { salesman ->
            salesman.areas.any { area ->
                matchArea(area, areaQuery)
            }
        }
    }

    private fun matchArea(area: String, query: String): Boolean {
        val normalizedArea = if (area.endsWith("*")) area.dropLast(1) else area
        return normalizedArea.startsWith(query) || query.startsWith(normalizedArea)
    }
}