package com.lukasgoulois.tp6goulois

class Dept(var id:Int, var nom:String, var chef:String, var technicien:String) {
    override fun toString(): String {
        return "id=$id, \nnom: $nom, chef: $chef, technicien: $technicien"
    }
}