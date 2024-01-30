
package com.example.a04_examenbi;
import android.content.ContentValues
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class SQLiteHelper(
    contexto:Context?
) : SQLiteOpenHelper(contexto, "moviles", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE IF NOT EXISTS empresas (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombre VARCHAR(50)," +
                    "telefono INTEGER," +
                    "estado BOOLEAN," +
                    "categoria VARCHAR(50));"
        )

        db?.execSQL(
            "CREATE TABLE IF NOT EXISTS empleados (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombre VARCHAR(50)," +
                    "apellido VARCHAR(50)," +
                    "edad INTEGER," +
                    "tiempo_completo BOOLEAN," +
                    "empresa_id INTEGER," +
                    "FOREIGN KEY(empresa_id) REFERENCES empresas(id));"
        )
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun crearEmpresa(empresa: BEmpresa): Long {
        val db = writableDatabase
        val values = ContentValues()
        values.put("nombre", empresa.nombreEmpresa)
        values.put("telefono", empresa.numeroCelular)
        values.put("estado", empresa.estado )
        values.put("categoria", empresa.categoria)
        return db.insert("empresas", null, values);
    }

    fun eliminarEmpresa(id:   Int):Boolean{
        val db = writableDatabase
        val parametrosColcutaDelete = arrayOf(id.toString())
        val resultado = db.delete("empresas", "id=?", parametrosColcutaDelete)
        db.close()
        return if (resultado.toInt() == -1 ) false else true
    }


    fun actualizarEmpresa(id:Int, empresaActualizada:BEmpresa) : Boolean{
        val empresa = BaseDatosMemoria.empresas.getOrNull(id);
        val db = writableDatabase
        val values = ContentValues()

        values.put("nombre", empresaActualizada.nombreEmpresa)
        values.put("telefono", empresaActualizada.numeroCelular)
        values.put("estado", empresaActualizada.estado)
        values.put("cateogoria", empresaActualizada.categoria)
        val parametrosColcutaActualiazar = arrayOf(id.toString())
        val resultado = db.update(
            "empresas", values, "id=?", parametrosColcutaActualiazar)
        db.close()
        return if (resultado.toInt() == -1 ) false else true
    }



}
