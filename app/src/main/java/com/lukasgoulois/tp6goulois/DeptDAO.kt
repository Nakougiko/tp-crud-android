package com.lukasgoulois.tp6goulois

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class DeptDAO {
    private lateinit var dbHelper: DeptHelper
    private lateinit var db: SQLiteDatabase

    private val VERSION = 1

     fun open(context: Context) {
        dbHelper = DeptHelper(context, "dept", null, VERSION)
        db = dbHelper.writableDatabase
    }

    fun close() {
        db.close()
    }

    // Permet d'ajouter un département
    fun addDept(d: Dept) : Long{
        val cv = ContentValues()
        cv.put(dbHelper.colId, d.id)
        cv.put(dbHelper.colNom, d.nom)
        cv.put(dbHelper.colChef, d.chef)
        cv.put(dbHelper.colTechnicien, d.technicien)
        return db.insert(dbHelper.table, null, cv)
    }

    // Permet de récuperer tous les départements
    fun getDepts() : Cursor {
        return db.query(dbHelper.table, null, null, null, null, null, null)
    }

    // Permet de déterminer le nombre de départements dans la base
    fun nbDepts() : Int {
        val cursor = db.query(dbHelper.table, null, null, null, null, null, null)
        return cursor.count
    }

    // Permet de selectionner tous les identifiants des départements dans la base
    fun getIds() : Cursor {
        return db.query(dbHelper.table, arrayOf(dbHelper.colId), null, null, null, null, null)
    }

    // Permet de sélectionner un departement via son id
    fun getDept(id: Int) : Cursor {
        return db.query(dbHelper.table, null, "${dbHelper.colId} = ${id}", arrayOf(id.toString()), null, null, null)
    }

    // permet de mettre a jour un département via son id
    fun editDept(id: Int, d: Dept) : Int {
        val cv = ContentValues()
        cv.put(dbHelper.colNom, d.nom)
        cv.put(dbHelper.colChef, d.chef)
        cv.put(dbHelper.colTechnicien, d.technicien)
        return db.update(dbHelper.table, cv, "${dbHelper.colId} = ?", arrayOf(id.toString()))
    }

    // Permet de supprimer un département
    fun deleteDept(id: Int) : Int {
        return db.delete(dbHelper.table, "${dbHelper.colId} = ?", arrayOf(id.toString()))
    }
}