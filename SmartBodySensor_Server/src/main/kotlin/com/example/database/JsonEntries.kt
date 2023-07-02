package com.example.database


import org.jetbrains.exposed.sql.Table

object JsonEntries : Table() {
    val id = integer("id").autoIncrement()
    val index = integer("index")
    val tempDifference = double("tempDifference")
    val voltage = double("voltage")

    override val primaryKey = PrimaryKey(id)
}