package modelo

import modelo.entidades.Empleado
import modelo.entidades.Empresa
import java.io.*

class EmpresaManager(private val empresas: MutableList<Empresa>) {

    fun listarEmpresas() {
        if (empresas.isEmpty()) {
            println("*************************************************************************")
            println("No hay empresas registradas. Regístralas seleccionando la segunda opción.")
            println("**************************************************************************")
            return
        }
        println("**********************************")
        println("EMPRESAS REGISTRADAS:")
        empresas.forEachIndexed { index, empresa ->
            println("${index + 1}. ${empresa.nombreEmpresa}")
        }
        println("**********************************")

        print("Seleccione una empresa para ver detalles (0 para volver): ")
        val seleccion = readLine()?.toIntOrNull()

        if (seleccion != null && seleccion in 1..empresas.size) {
            val empresaSeleccionada = empresas[seleccion - 1]
            subMenuEmpresa(empresaSeleccionada)
        } else if (seleccion != null && seleccion == 0) {
            // Volver al menú principal
        } else {
            println("Selección no válida.")
        }
    }

    fun crearNuevaEmpresa() {
        print("Nombre de la nueva empresa: ")
        val nombre = readLine() ?: ""
        print("Número de empleados: ")
        val numEmpleados = readLine()?.toIntOrNull() ?: 0
        print("Ingreso anual: ")
        val ingresoAnual = readLine()?.toDoubleOrNull() ?: 0.0
        print("Ubicación: ")
        val ubicacion = readLine() ?: ""
        print("Es internacional (true/false): ")
        val esInternacional = readLine()?.toBooleanOrNull() ?: false

        val nuevaEmpresa = Empresa(nombre, numEmpleados, ingresoAnual, ubicacion, esInternacional)
        empresas.add(nuevaEmpresa)

        println("Nueva empresa creada correctamente.")
    }

    fun subMenuEmpresa(empresa: Empresa) {
        do {
            println("\nEmpresa: ${empresa.nombreEmpresa}")
            println("1. Listar Empleados")
            println("2. Agregar Empleado")
            println("3. Actualizar Empleado")
            println("4. Eliminar Empleado")
            println("0. Volver")

            print("Seleccione una opción: ")
            val opcion = readLine()?.toIntOrNull()

            when (opcion) {
                1 -> listarEmpleados(empresa)
                2 -> agregarEmpleado(empresa)
                3 -> actualizarEmpleado(empresa)
                4 -> eliminarEmpleado(empresa)
                0 -> println("Volviendo a la lista de empresas.")
                else -> println("Opción no válida, por favor intente de nuevo.")
            }

        } while (opcion != 0)
    }

    fun listarEmpleados(empresa: Empresa) {
        if (empresa.listaEmpleados.isEmpty()) {
            println("**********************************")
            println("No hay empleados registrados en ${empresa.nombreEmpresa}.")
            println("**********************************")
        } else {
            println("Empleados en ${empresa.nombreEmpresa}:")
            empresa.listaEmpleados.forEachIndexed { index, empleado ->
                println("${index + 1}. ${empleado.nombreEmpleado} ${empleado.apellidoEmpleado}")
            }
        }
    }

    fun agregarEmpleado(empresa: Empresa) {
        print("Nombre del empleado: ")
        val nombre = readLine() ?: ""
        print("Apellido del empleado: ")
        val apellido = readLine() ?: ""
        print("Es tiempo completo (true/false): ")
        val tiempoCompleto = readLine()?.toBooleanOrNull() ?: false
        print("Salario: ")
        val salario = readLine()?.toDoubleOrNull() ?: 0.0
        print("Número de vacaciones: ")
        val numVacaciones = readLine()?.toIntOrNull() ?: 0

        val nuevoEmpleado = Empleado(nombre, apellido, tiempoCompleto, salario, numVacaciones)
        empresa.listaEmpleados.add(nuevoEmpleado)

        println("Empleado agregado correctamente.")
    }

    fun actualizarEmpleado(empresa: Empresa) {
        if (empresa.listaEmpleados.isEmpty()) {
            println("No hay empleados registrados en ${empresa.nombreEmpresa}.")
            return
        }

        println("Seleccione un empleado para actualizar:")
        empresa.listaEmpleados.forEachIndexed { index, empleado ->
            println("${index + 1}. ${empleado.nombreEmpleado} ${empleado.apellidoEmpleado}")
        }

        print("Seleccione un empleado (0 para cancelar): ")
        val seleccion = readLine()?.toIntOrNull()

        if (seleccion != null && seleccion in 1..empresa.listaEmpleados.size) {
            val empleadoSeleccionado = empresa.listaEmpleados[seleccion - 1]

            print("Nuevo nombre del empleado: ")
            empleadoSeleccionado.nombreEmpleado = readLine() ?: empleadoSeleccionado.nombreEmpleado
            print("Nuevo apellido del empleado: ")
            empleadoSeleccionado.apellidoEmpleado = readLine() ?: empleadoSeleccionado.apellidoEmpleado
            print("Es tiempo completo (true/false): ")
            empleadoSeleccionado.esTiempoCompleto = readLine()?.toBooleanOrNull()
                ?: empleadoSeleccionado.esTiempoCompleto
            print("Nuevo salario: ")
            empleadoSeleccionado.salario = readLine()?.toDoubleOrNull() ?: empleadoSeleccionado.salario
            print("Nuevo número de vacaciones: ")
            empleadoSeleccionado.numeroVacaciones = readLine()?.toIntOrNull()
                ?: empleadoSeleccionado.numeroVacaciones

            println("Empleado actualizado correctamente.")
        } else if (seleccion != null && seleccion == 0) {
            // Cancelar la operación
        } else {
            println("Selección no válida.")
        }
    }

    fun eliminarEmpleado(empresa: Empresa) {
        if (empresa.listaEmpleados.isEmpty()) {
            println("No hay empleados registrados en ${empresa.nombreEmpresa}.")
            return
        }

        println("Seleccione un empleado para eliminar:")
        empresa.listaEmpleados.forEachIndexed { index, empleado ->
            println("${index + 1}. ${empleado.nombreEmpleado} ${empleado.apellidoEmpleado}")
        }

        print("Seleccione un empleado (0 para cancelar): ")
        val seleccion = readLine()?.toIntOrNull()

        if (seleccion != null && seleccion in 1..empresa.listaEmpleados.size) {
            val empleadoSeleccionado = empresa.listaEmpleados[seleccion - 1]
            empresa.listaEmpleados.remove(empleadoSeleccionado)
            println("Empleado eliminado correctamente.")
        } else if (seleccion != null && seleccion == 0) {
            // Cancelar la operación
        } else {
            println("Selección no válida.")
        }
    }

    fun String?.toBooleanOrNull(): Boolean? {
        return when (this?.toLowerCase()) {
            "true" -> true
            "false" -> false
            else -> null
        }
    }
}