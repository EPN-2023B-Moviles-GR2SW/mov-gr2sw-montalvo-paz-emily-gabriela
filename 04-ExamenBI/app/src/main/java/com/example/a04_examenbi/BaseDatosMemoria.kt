package com.example.a04_examenbi

import com.google.firebase.Firebase
import com.google.firebase.firestore.FieldValue
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


        fun eliminarEmpresa(empresaId: String, param: (Any, Any) -> Unit) {
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
        fun cargarEmpleados(empresaId: String, callback: (List<BEmpleado>?, error: String?) -> Unit) {
            val db = Firebase.firestore
            val empresasRef = db.collection("empresas")
            empresasRef.document(empresaId).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val empresa = document.toObject(BEmpresa::class.java)
                        callback(empresa?.empleados, null)
                    } else {
                        callback(null, "Empresa no encontrada: $empresaId")
                    }
                }
                .addOnFailureListener { e ->
                    callback(null, "Error al cargar empleados: $e")
                }
        }


        fun agregarEmpleado(empresaId: String, empleado: BEmpleado, callback: (Boolean, error: String?) -> Unit) {
            val db = Firebase.firestore
            val empresasRef = db.collection("empresas")
            empresasRef.document(empresaId).update("empleados", FieldValue.arrayUnion(empleado))
                .addOnSuccessListener {
                    callback(true, null)
                }
                .addOnFailureListener { e ->
                    callback(false, "Error al agregar empleado: $e")
                }
        }

        fun actualizarEmpleado(empresaId: String, empleado: BEmpleado, empleadoActualizado: BEmpleado, callback: (Boolean, error: String?) -> Unit) {
            val db = Firebase.firestore
            val empresasRef = db.collection("empresas")
            empresasRef.document(empresaId).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val empresa = document.toObject(BEmpresa::class.java)
                        empresa?.empleados?.let { empleados ->
                            val empleadoExistente = empleados.find { it.nombreEmpleado == empleado.nombreEmpleado && it.apellidoEmpleado == empleado.apellidoEmpleado }
                            if (empleadoExistente != null) {
                                empleados[empleados.indexOf(empleadoExistente)] = empleadoActualizado
                                empresasRef.document(empresaId).update("empleados", empleados)
                                    .addOnSuccessListener {
                                        callback(true, null)
                                    }
                                    .addOnFailureListener { e ->
                                        callback(false, "Error al actualizar empleado: $e")
                                    }
                            } else {
                                callback(false, "Empleado no encontrado en la empresa")
                            }
                        }
                    } else {
                        callback(false, "Empresa no encontrada: $empresaId")
                    }
                }
                .addOnFailureListener { e ->
                    callback(false, "Error al actualizar empleado: $e")
                }
        }


        fun eliminarEmpleado(empresaId: String, empleadoId: String, callback: (Boolean, error: String?) -> Unit) {
            val db = Firebase.firestore
            val empresasRef = db.collection("empresas")
            empresasRef.document(empresaId).update("empleados", FieldValue.arrayRemove(empleadoId))
                .addOnSuccessListener {
                    callback(true, null)
                }
                .addOnFailureListener { e ->
                    callback(false, "Error al eliminar empleado: $e")
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
