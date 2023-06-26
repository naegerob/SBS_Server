package com.example.plugins

import org.jetbrains.exposed.sql.Table
object JsonEntries : Table() {
    val id = integer("id").autoIncrement()
    val title = varchar("title", 128)
    val body = varchar("body", 1024)

    override val primaryKey = PrimaryKey(id)
}