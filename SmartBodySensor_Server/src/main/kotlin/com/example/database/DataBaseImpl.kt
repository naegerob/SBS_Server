package com.example.database

import com.example.dao.DAOFacade
import com.example.dao.DatabaseFactory.dbQuery
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class DataBaseImpl : DAOFacade {
    /*
    * Helper function which transforms the entries in the database
    * into the object JsonEntry
    * */
    private fun resultRowToEntry(row: ResultRow) = JsonEntry(
            id = row[JsonEntries.id],
            index = row[JsonEntries.index],
            tempDifference = row[JsonEntries.tempDifference],
            batteryLevel = row[JsonEntries.voltage],
        )

    override suspend fun allEntries(): List<JsonEntry> = dbQuery  {
        JsonEntries.selectAll().map(::resultRowToEntry)
    }

    override suspend fun entry(id: Int): JsonEntry? = dbQuery {
        JsonEntries
            .select{JsonEntries.id eq id}
            .map(::resultRowToEntry)
            .singleOrNull()
    }

    override suspend fun addNewEntry(index: Int, tempDifference: Double, voltage: Double): JsonEntry? = dbQuery {
        val insertStatement = JsonEntries.insert {
            it[JsonEntries.index] = index
            it[JsonEntries.tempDifference] = tempDifference
            it[JsonEntries.voltage] = voltage
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToEntry)
    }

    override suspend fun editEntry(id: Int, index: Int, tempDifference: Double, voltage: Double): Boolean = dbQuery {
       JsonEntries.update ({ JsonEntries.id eq id}) {
           it[JsonEntries.index] = index
           it[JsonEntries.tempDifference] = tempDifference
           it[JsonEntries.voltage] = voltage
       } > 0
    }

    override suspend fun deleteEntry(id: Int): Boolean = dbQuery {
        JsonEntries.deleteWhere { JsonEntries.id eq id } > 0
    }
}

val dao: DAOFacade = DataBaseImpl().apply {
    runBlocking {
        if(allEntries().isEmpty()) {
            addNewEntry(1, 5.2, 3.6)
        }
    }
}