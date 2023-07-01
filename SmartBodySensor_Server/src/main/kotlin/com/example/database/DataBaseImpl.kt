package com.example.database

import com.example.dao.DAOFacade
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.ResultRow

class DataBaseImpl : DAOFacade {

    private fun resultRowToEntry(row: ResultRow) = JsonEntry(


            id = row[JsonEntry.id],
            index = row[JsonEntry.index],
            tempDifference = row[JsonEntry.tempDifference],
            batteryLevel = row[JsonEntry.voltage],
        )

    override suspend fun allEntries(): List<JsonEntry> {
        TODO("Not yet implemented")
    }

    override suspend fun entry(id: Int): JsonEntry? {
        TODO("Not yet implemented")
    }

    override suspend fun addNewEntry(title: String, body: String): JsonEntry? {
        TODO("Not yet implemented")
    }

    override suspend fun editEntry(id: Int, title: String, body: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deleteEntry(id: Int): Boolean {
        TODO("Not yet implemented")
    }
}