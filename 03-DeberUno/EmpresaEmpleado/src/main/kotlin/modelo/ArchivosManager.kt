import modelo.entidades.Empresa
import modelo.entidades.Empleado
import java.io.File

class ArchivosManager(private val archivo: String) {
    fun guardarDatosEnArchivo(empresas: List<Empresa>) {
        try {
            val archivoTexto = File(archivo)
            archivoTexto.bufferedWriter().use { writer ->
                empresas.forEach { empresa ->
                    with(empresa) {
                        writer.write("Nombre Empresa: $nombreEmpresa\n")
                        writer.write("Número Empleados: $numeroEmpleados\n")
                        writer.write("Ingreso Anual: $ingresoAnual\n")
                        writer.write("Ubicación: $ubicacion\n")
                        writer.write("Es Internacional: ${if (esInternacional) "Sí" else "No"}\n")

                        listaEmpleados.forEach { empleado ->
                            with(empleado) {
                                writer.write("  Nombre Empleado: $nombreEmpleado\n")
                                writer.write("  Apellido Empleado: $apellidoEmpleado\n")
                                writer.write("  Empleado Tiempo Completo: ${if (esTiempoCompleto) "Sí" else "No"}\n")
                                writer.write("  Salario: $salario\n")
                                writer.write("  Días Vacaciones: $numeroVacaciones\n")
                            }
                        }

                        writer.write("\n")
                    }
                }
            }
            println("Datos guardados en el archivo de texto legible.")
        } catch (ex: Exception) {
            println("Error al guardar los datos en el archivo de texto legible.")
        }
    }

    fun cargarDatosDesdeArchivo(): List<Empresa> {
        val empresas = mutableListOf<Empresa>()

        try {
            val archivoTexto = File(archivo)
            if (archivoTexto.exists()) {
                val lineas = archivoTexto.readLines()

                var empresa: Empresa? = null

                for (linea in lineas) {
                    val partes = linea.split(": ")
                    if (partes.size == 2) {
                        when (partes[0]) {
                            "Nombre Empresa" -> {
                                empresa?.let {
                                    empresas.add(it)
                                }
                                val nombreEmpresa = partes[1]
                                empresa = Empresa(nombreEmpresa, 0, 0.0, "", false, mutableListOf())
                            }
                            "Número Empleados" -> empresa?.numeroEmpleados = partes[1].toInt()
                            "Ingreso Anual" -> empresa?.ingresoAnual = partes[1].toDouble()
                            "Ubicación" -> empresa?.ubicacion = partes[1]
                            "Es Empresa Internacional" -> empresa?.esInternacional = partes[1] == "Sí"
                            "Nombre Empleado" -> {
                                val nombreEmpleado = partes[1]
                                val apellidoEmpleado = lineas[lineas.indexOf(linea) + 1].split(": ")[1]
                                val esTiempoCompleto = lineas[lineas.indexOf(linea) + 2].split(": ")[1] == "Sí"
                                val salario = lineas[lineas.indexOf(linea) + 3].split(": ")[1].toDouble()
                                val numeroVacaciones = lineas[lineas.indexOf(linea) + 4].split(": ")[1].toInt()
                                empresa?.listaEmpleados?.add(
                                    Empleado(nombreEmpleado, apellidoEmpleado, esTiempoCompleto, salario, numeroVacaciones)
                                )
                            }
                        }
                    }
                }

                empresa?.let {
                    empresas.add(it)
                }
            }
        } catch (ex: Exception) {
            println("Error al cargar los datos desde el archivo de texto legible.")
        }

        return empresas
    }

}
