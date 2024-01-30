package com.example.a04_examenbi

class BaseDatosMemoria {

    companion object{
        var tablaEmpresa : SQLiteHelper? = null
        val empresas = arrayListOf<BEmpresa>()

        //Empresas
        fun crearEmpresa(empresa: BEmpresa){
            empresas.add(empresa)
        }

        fun buscarEmpresa(empresaId: Int):BEmpresa?{
            val empresa = empresas.getOrNull(empresaId);
            if(empresa != null){
                return empresa
            }else{
                println("Empresa no encontrada ${empresaId}");
                return null;
            }
        }

        fun actualizarEmpresa(id:Int, empresaActualizada:BEmpresa){
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
            var listaEmpleados: MutableList<BEmpleado> = mutableListOf();
            for(id in empresa.empleados.indices){
                println("${id}: ${empresa.empleados.get(id)}");
                listaEmpleados.add(empresa.empleados.get(id))
            }
            return listaEmpleados;
        }

        fun agregarEmpleado(empresaId: Int, empleado: BEmpleado){
            val empresa = empresas.getOrNull(empresaId);
            if(empresa != null){
                empresa.empleados.add(empleado);
                println("Empleado Agregado '${empresa.nombreEmpresa}'.")
            }else{
                println("Empresa no Encontrada con:  ${empresaId}");
            }
        }

        fun actualizarEmpleado(empresaId: Int,empleadoId: Int, empleadoActualizado: BEmpleado){
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