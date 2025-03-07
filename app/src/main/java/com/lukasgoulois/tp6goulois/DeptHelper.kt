package com.lukasgoulois.tp6goulois

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DeptHelper(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {
    val table = "dept"
    val colId = "id"
    val colNom = "nom"
    val colChef = "chef"
    val colTechnicien = "technicien"

    val sqlTable = "CREATE TABLE ${table} (" +
            "${colId} INTEGER PRIMARY KEY AUTOINCREMENT," +
            "${colNom} TEXT NOT NULL," +
            "${colChef} TEXT NOT NULL," +
            "${colTechnicien} TEXT NOT NULL)"

    val sqlDropTable = "DROP TABLE IF EXISTS ${table}"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(sqlTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(sqlDropTable)
        onCreate(db)
    }
}