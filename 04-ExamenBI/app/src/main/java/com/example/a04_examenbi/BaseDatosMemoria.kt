package com.example.a04_examenbi

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class BaseDatosMemoria {

    companion object{
        var tablaEmpresa : SQLiteHelper? = null
        val empresas = arrayListOf<BEmpresa>()

        //Empresas
        fun crearEmpresa(empresa: BEmpresa){
            val db = Firebase.firestore
            empresas.add(empresa)
        }

        fun buscarEmpresa(empresaId: Int):BEmpresa?{
            val db = Firebase.firestore
            val empresa = empresas.getOrNull(empresaId);
            if(empresa != null){
                return empresa
            }else{
                println("Empresa no encontrada ${empresaId}");
                return null;
            }
        }

        fun actualizarEmpresa(id:Int, empresaActualizada:BEmpresa){
            val db = Firebase.firestore
            val empresa = empresas.getOrNull(id);
            if(empresa != null){
                println("Empresa seleccionada: $empresa")
                empresas[id] = empresaActualizada
                println("Empresa actualizada: $empresaActualizada")
            }else{
                println("Empresa no encontrada: ${id}")
            }

        }

        fun eliminarEmpresa(id:   Int):Boolean{
            val db = Firebase.firestore
            val empresa = empresas.getOrNull(id);
            if(empresa != null){
                empresas.remove(empresa);
                println("Empresa eliminada: $empresa");
                return true;
            }else{
                println("Empresa no encontrada ${id}");
                return false;
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
            empresas.add(
                BEmpresa(
                    "Procredit",
                    99599046,
                    true,
                    "Financiera",
                    empleados = mutableListOf(
                        BEmpleado(
                            "Emily",
                            "Montalvo",
                            48,
                            true
                        ),
                        BEmpleado(
                            "John",
                            "Doe",
                            30,
                            true
                        )
                    )
                )
            )

            fun crearDatosPrueba() {
                val db = Firebase.firestore

                val empresas = db.collection("empresas")

                val empleado1 = hashMapOf(
                    "nombreEmpleado" to "Eva",
                    "apellidoEmpleado" to "Luna",
                    "edadEmpleado" to 15,
                    "tiempoCompleto" to true
                )

                val data1 = hashMapOf(
                    "nombreEmpresa" to "TechCorp",
                    "numeroCelular" to 2891475,
                    "estado" to false,
                    "categoria" to "Tecnologia",
                    "regions" to listOf(empleado1)
                )
                empresas.document("TC").set(data1)

                val data2 = hashMapOf(
                    "name" to "Los Angeles",
                    "state" to "CA",
                    "country" to "USA",
                    "capital" to false,
                    "population" to 3900000,
                    "regions" to listOf("west_coast", "socal"),
                )
                empresas.document("LA").set(data2)

                val data3 = hashMapOf(
                    "name" to "Washington D.C.",
                    "state" to null,
                    "country" to "USA",
                    "capital" to true,
                    "population" to 680000,
                    "regions" to listOf("east_coast"),
                )
                empresas.document("DC").set(data3)

                val data4 = hashMapOf(
                    "name" to "Tokyo",
                    "state" to null,
                    "country" to "Japan",
                    "capital" to true,
                    "population" to 9000000,
                    "regions" to listOf("kanto", "honshu"),
                )
                empresas.document("TOK").set(data4)

                val data5 = hashMapOf(
                    "name" to "Beijing",
                    "state" to null,
                    "country" to "China",
                    "capital" to true,
                    "population" to 21500000,
                    "regions" to listOf("jingjinji", "hebei"),
                )
                empresas.document("BJ").set(data5)
            }
            empresas.add(
                BEmpresa(
                    "TechCorp",
                    98765432,
                    true,
                    "Tecnología",
                    empleados = mutableListOf(
                        BEmpleado(
                            "Alice",
                            "Johnson",
                            25,
                            true
                        ),
                        BEmpleado(
                            "Bob",
                            "Smith",
                            35,
                            true
                        ),
                        BEmpleado(
                            "Charlie",
                            "Brown",
                            28,
                            true
                        )
                    )
                )
            )

            empresas.add(
                BEmpresa(
                    "FoodExpress",
                    91234567,
                    true,
                    "Alimentación",
                    empleados = mutableListOf(
                        BEmpleado(
                            "Eva",
                            "Gonzalez",
                            22,
                            true
                        ),
                        BEmpleado(
                            "David",
                            "Clark",
                            40,
                            true
                        ),
                        BEmpleado(
                            "Sophia",
                            "Taylor",
                            32,
                            true
                        )
                    )
                )
            )
        }

    }


}