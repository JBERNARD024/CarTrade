package pt.ipt.dam2023.cartrade

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context:Context): SQLiteOpenHelper(context, "Utilizador", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table Utilizador (username TEXT primary key, email TEXT, password TEXT)" )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("drop table if exists Utilizador")
    }

    fun inserirDados(username:String, email:String, password:String): Boolean {
        val db = this.writableDatabase
        val content = ContentValues()
        content.put("username", username)
        content.put("email", email)
        content.put("password", password)
        val resultado = db.insert("Utilizador", null, content)
        if(resultado==-1 .toLong()){
            return false
        }else{
            return true
        }
    }

    fun verificaUtilizador(email:String, password: String): Boolean{
        val db = this.writableDatabase
        val query = "select * from Utilizador where email = '$email' and password = '$password'"
        val cursor = db.rawQuery(query, null)
        if(cursor.count<=0){
            cursor.close()
            return false
        }
        cursor.close()
        return true
    }
}