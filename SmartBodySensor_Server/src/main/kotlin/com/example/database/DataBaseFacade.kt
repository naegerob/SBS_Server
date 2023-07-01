package com.example.dao

import com.example.database.JsonEntry

interface DAOFacade {
    suspend fun allEntries(): List<JsonEntry>
    suspend fun entry(id: Int): JsonEntry?
    suspend fun addNewEntry(title: String, body: String): JsonEntry?
    suspend fun editEntry(id: Int, title: String, body: String): Boolean
    suspend fun deleteEntry(id: Int): Boolean
}