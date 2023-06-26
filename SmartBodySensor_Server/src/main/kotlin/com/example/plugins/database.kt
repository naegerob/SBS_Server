package com.example.plugins

import org.jetbrains.exposed.sql.Table

object JsonEntries : Table() {
    val id = integer("id").autoIncrement()
    val index = varchar("index", 128)
    val tempDifference = varchar("title", 128)
    val voltage = varchar("body", 1024)

    override val primaryKey = PrimaryKey(id)
}