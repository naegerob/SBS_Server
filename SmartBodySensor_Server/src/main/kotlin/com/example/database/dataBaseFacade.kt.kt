package com.example.dao

import com.example.database.JsonEntry

interface DAOFacade {
    suspend fun allArticles(): List<JsonEntry>
    suspend fun article(id: Int): JsonEntry?
    suspend fun addNewArticle(title: String, body: String): JsonEntry?
    suspend fun editArticle(id: Int, title: String, body: String): Boolean
    suspend fun deleteArticle(id: Int): Boolean
}