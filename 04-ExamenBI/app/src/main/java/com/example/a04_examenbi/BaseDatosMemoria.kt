package com.example.a04_examenbi

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class BaseDatosMemoria {

    companion object{
        var tablaEmpresa : SQLiteHelper? = null
        val empresas = arrayListOf<BEmpresa>()

        //Empresas
        fun crearEmpresa(empresa: BEmpresa) {
            val db = Firebase.firestore
            val empresasRef = db.collection("empresas")
            empresa.nombreEmpresa?.let {
                empresasRef.document(it).set(empresa)
                    .addOnSuccessListener {
                        println("Empresa creada exitosamente")
                    }
                    .addOnFailureListener { e ->
                        println("Error al crear empresa: $e")
                    }
            }
        }

        fun buscarEmpresa(empresaId: String, callback: (BEmpresa?) -> Unit) {
            val db = Firebase.firestore
            val empresasRef = db.collection("empresas")
            empresasRef.document(empresaId).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val empresa = document.toObject(BEmpresa::class.java)
                        callback(empresa)
                    } else {
                        println("Empresa no encontrada: $empresaId")
                        callback(null)
                    }
                }
                .addOnFailureListener { e ->
                    println("Error al buscar empresa: $e")
                    callback(null)
                }
        }


        fun actualizarEmpresa(empresaId: String, empresaActualizada: BEmpresa) {
            val db = Firebase.firestore
            val empresasRef = db.collection("empresas")
            empresasRef.document(empresaId).set(empresaActualizada)
                .addOnSuccessListener {
                    println("Empresa actualizada exitosamente: $empresaActualizada")
                }
                .addOnFailureListener { e ->
                    println("Error al actualizar empresa: $e")
                }
        }


        fun eliminarEmpresa(empresaId: String) {
            val db = Firebase.firestore
            val empresasRef = db.collection("empresas")
            empresasRef.document(empresaId).delete()
                .addOnSuccessListener {
                    println("Empresa eliminada exitosamente: $empresaId")
                }
                .addOnFailureListener { e ->
                    println("Error al eliminar empresa: $e")
                }
        }

        // EMPLEADOS
        fun cargarEmpleados(empresa: BEmpresa):MutableList<BEmpleado>{
            val db = Firebase.firestore
            var listaEmpleados: MutableList<BEmpleado> = mutableListOf();
            for(id in empresa.empleados.indices){
                println("${id}: ${empresa.empleados.get(id)}");
                listaEmpleados.add(empresa.empleados.get(id))
            }
            return listaEmpleados;
        }

        fun agregarEmpleado(empresaId: Int, empleado: BEmpleado){
            val db = Firebase.firestore
            val empresa = empresas.getOrNull(empresaId);
            if(empresa != null){
                empresa.empleados.add(empleado);
                println("Empleado Agregado '${empresa.nombreEmpresa}'.")
            }else{
                println("Empresa no Encontrada con:  ${empresaId}");
            }
        }

        fun actualizarEmpleado(empresaId: Int,empleadoId: Int, empleadoActualizado: BEmpleado){
            val db = Firebase.firestore
            val empresa = empresas.getOrNull(empresaId);
            if(empresa != null){
                val empleado = empresa.empleados.getOrNull(empleadoId);
                if(empleado != null){
                    empresa.empleados[empleadoId] = empleadoActualizado;
                }else{
                    println("Empleado no encontrado con id: ${empleadoId}'.")
                }
            }else{
                println("Empresa no encontrada con id:  ${empresaId}");
            }
        }


        fun eliminarEmpleado(empresId:Int, empleadoId:Int ):Boolean{
            val db = Firebase.firestore
            val empresa = empresas.getOrNull(empresId);
            if(empresa != null){
                val empleado = empresa.empleados.getOrNull(empleadoId);
                if(empleado != null){
                    empresa.empleados.remove(empleado);
                    println("Empleado eliminado")
                    return true;
                }else{
                    println("Empresa no encontrada");
                    return false;
                }
            }else{
                println("Empresa no encontrada");
                return false;
            }
        }

        init {
            fun crearDatosPrueba() {
                val db = Firebase.firestore

                val empresas = db.collection("empresas")

                val empleado1 = hashMapOf(
                    "nombreEmpleado" to "Eva",
                    "apellidoEmpleado" to "Luna",
                    "edadEmpleado" to 15,
                    "tiempoCompleto" to true
                )
                val empleado2 = hashMapOf(
                    "nombreEmpleado" to "Camilo",
                    "apellidoEmpleado" to "Luna",
                    "edadEmpleado" to 15,
                    "tiempoCompleto" to true
                )
                val empleado3 = hashMapOf(
                    "nombreEmpleado" to "Indigo",
                    "apellidoEmpleado" to "Luna",
                    "edadEmpleado" to 15,
                    "tiempoCompleto" to true
                )

                val data1 = hashMapOf(
                    "nombreEmpresa" to "TechCorp",
                    "numeroCelular" to 2891475,
                    "estado" to false,
                    "categoria" to "Tecnologia",
                    "empleados" to listOf(empleado1, empleado2, empleado3)
                )
                empresas.document("TC").set(data1)

                val data2 = hashMapOf(
                    "nombreEmpresa" to "KFC",
                    "numeroCelular" to 28914755454,
                    "estado" to true,
                    "categoria" to "Finanzas",
                    "empleados" to listOf(empleado2, empleado3)
                )
                empresas.document("KFC").set(data2)

                val data3 = hashMapOf(
                    "nombreEmpresa" to "Pollo Campero",
                    "numeroCelular" to 2454,
                    "estado" to true,
                    "categoria" to "Comida",
                    "empleados" to listOf(empleado1, empleado3)
                )
                empresas.document("PC").set(data3)

                val data4 = hashMapOf(
                    "nombreEmpresa" to "Pandora",
                    "numeroCelular" to 24548453,
                    "estado" to false,
                    "categoria" to "Accesosrios",
                    "empleados" to listOf(empleado1, empleado3, empleado2)
                )
                empresas.document("TOK").set(data4)


            }
        }

    }


}