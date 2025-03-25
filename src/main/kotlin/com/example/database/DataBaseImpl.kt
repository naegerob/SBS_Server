package com.example.database

import com.example.dao.DAOFacade
import com.example.dao.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class DataBaseImpl : DAOFacade {


    /**
     * Helper function which transforms the entries in the database
     * into the object JsonEntry
     */
    private fun resultRowToEntry(row: ResultRow) = JsonEntry(
            id = row[JsonEntries.id],
            index = row[JsonEntries.index],
            tempDifference = row[JsonEntries.tempDifference],
            batteryLevel = row[JsonEntries.voltage],
        )

    /**
     * Returns all entries from the database
     */
    override suspend fun allEntries(): List<JsonEntry> = dbQuery  {
        JsonEntries.selectAll().map(::resultRowToEntry)
    }

    /**
     * returns an entry which matches the specific id
     */
    override suspend fun entry(id: Int): JsonEntry? = dbQuery {
        JsonEntries
            .select{JsonEntries.id eq id}
            .map(::resultRowToEntry)
            .singleOrNull()
    }

    /**
     * Insert a new entry in the db. The id will be generated
     */
    override suspend fun addNewEntry(index: Int, tempDifference: Double, voltage: Double): JsonEntry? = dbQuery {
        val insertStatement = JsonEntries.insert {
            it[JsonEntries.index] = index
            it[JsonEntries.tempDifference] = tempDifference
            it[JsonEntries.voltage] = voltage
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToEntry)
    }

    /**
     * update existing entry in db.
     */
    override suspend fun editEntry(id: Int, index: Int, tempDifference: Double, voltage: Double): Boolean = dbQuery {
       JsonEntries.update ({ JsonEntries.id eq id}) {
           it[JsonEntries.index] = index
           it[JsonEntries.tempDifference] = tempDifference
           it[JsonEntries.voltage] = voltage
       } > 0
    }

    /**
     * delete entry and returns boolean if successful.
     */
    override suspend fun deleteEntry(id: Int): Boolean = dbQuery {
        JsonEntries.deleteWhere { JsonEntries.id eq id } > 0
    }

    override suspend fun deleteAll() : Boolean = dbQuery {
        JsonEntries.deleteAll() > 0
    }

    override suspend fun existsEntry(id: Int): Boolean  = dao.entry(id) is JsonEntry

}
/**
 * Initialization
 */
val dao: DAOFacade = DataBaseImpl()
