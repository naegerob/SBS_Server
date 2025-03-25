package com.example.dao

import com.example.database.JsonEntry

interface DAOFacade {
    suspend fun allEntries(): List<JsonEntry>
    suspend fun entry(id: Int): JsonEntry?
    suspend fun addNewEntry(index: Int, tempDifference: Double, voltage: Double): JsonEntry?
    suspend fun editEntry(id: Int, index: Int, tempDifference: Double, voltage: Double): Boolean
    suspend fun deleteEntry(id: Int): Boolean
    suspend fun deleteAll(): Boolean
    suspend fun existsEntry(id: Int): Boolean
}