package com.example.b2023gr2sw

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ESqliteHelperEntrenator(
    contexto: Context?, //THIS
): SQLiteOpenHelper( contexto, "moviles", null, 1) {

    override  fun onCreate(db:SQLiteDatabase?){
        val scriptSQLCrearTablaBEntrenador =
            """
                CREATE TABLE ENTRENADOR(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                 nombre VARCHAR(50),
                 descripcion VARCHAR(50)
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaBEntrenador)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    fun crearEntrenador(
        nombre:String,
        descripcion: String
    ): Boolean{
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("descripcion", descripcion)
        val resultadoGuardar = basedatosEscritura.insert("ENTRENADOR", null, valoresAGuardar)
        basedatosEscritura.close()
        return if (resultadoGuardar.toInt()==-1) false else true
    }


    fun eliminarEntrenadorFormulario(id:Int):Boolean{
        val conexionEscritura = writableDatabase
        val parametrosConsultaDelete = arrayOf(id.toString())
        val resultadoEliminacion = conexionEscritura.delete("ENTRENADOR", "id=?", parametrosConsultaDelete)
        conexionEscritura.close()
        return if (resultadoEliminacion.toInt() ==-1) false else true
    }

     fun actualizarEntrenadorFOrmulario(
         nombre: String,
         descripcion: String,
         id: Int
     ) : Boolean{
         val conexionEscritura = writableDatabase
         val valoresActualizar = ContentValues()
         valoresActualizar.put("nombre", nombre)
         valoresActualizar.put("descripcion", descripcion)
         val parametrosConsultaActualizar = arrayOf(id.toString())
         val resultadoActualizacion = conexionEscritura.update(
             "ENTRENADOR",
             valoresActualizar,
             "id=?", //CONSULTA WHERE
             parametrosConsultaActualizar
         )
         conexionEscritura.close()
         return if (resultadoActualizacion.toInt() ==-1) false else true
     }


    fun consultarEntrenadorPorID(id: Int): BEntrenador{
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM ENTRENADOR WHERE ID = ?
        """.trimIndent()
        val parametrosConsultaLectura = arrayOf(id.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura, //consulta
            parametrosConsultaLectura, //parametros
        )
        //lógica busqueda
        val existeUsuario =  resultadoConsultaLectura.moveToFirst()
        val usuarioEncontrado = BEntrenador(0, "", "")
        val arreglo = arrayListOf<BEntrenador>()
        if(existeUsuario){
            do{
                val id = resultadoConsultaLectura.getInt(0) //indice 0
                val nombre = resultadoConsultaLectura.getString(1)
                val descripcion = resultadoConsultaLectura.getString(2)
                if(id != null){
                    //llenar el arreglo con el nuevo BEntrenador
                    usuarioEncontrado.id = id
                    usuarioEncontrado.nombre = nombre
                    usuarioEncontrado.descripcion = descripcion
                }
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return usuarioEncontrado
    }

}

