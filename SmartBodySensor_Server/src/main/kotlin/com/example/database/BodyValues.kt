package com.example.database

import org.jetbrains.exposed.sql.Table

object BodyValues : Table() {
    private val id = integer("id").autoIncrement()
    private val index = varchar("index", 128)
    private val tempDifference = varchar("tempDifference", 128)
    private val voltage = varchar("voltage", 128)

    override val primaryKey = PrimaryKey(id)
}