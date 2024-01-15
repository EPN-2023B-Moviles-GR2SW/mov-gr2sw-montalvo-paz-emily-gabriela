package com.example.a04_examenbi

class BaseDatosMemoria {

    companion object{
        val empresas = arrayListOf<BEmpresa>()

        /////////////////////// CRUD DE EMPRESAS /////////////////////
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
                println("Empresa actualizada: $empresa")
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

        /////////////////////// CRUD EMPLEADOS //////////////////////////////////
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
                    println("Empresa no enocntrada");
                    return false;
                }
            }else{
                println("Empresa no encontrada");
                return false;
            }
        }





        init {
            empresas.add(BEmpresa(
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
                            )
                        )
                    )
                )

        }
    }
}