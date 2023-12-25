package controlador

import ArchivosManager
import modelo.EmpresaManager
import modelo.EmpresaMenu
import modelo.entidades.Empresa

fun main() {
    val archivosManager = ArchivosManager("empresas.txt")
    val empresas = archivosManager.cargarDatosDesdeArchivo().toMutableList()

    val empresaManager = EmpresaManager(empresas, archivosManager)
    val empresaMenu = EmpresaMenu(empresaManager)

    do {
        empresaMenu.mostrarMenu()
        val opcion = readLine()?.toIntOrNull()

        when (opcion) {
            0 -> {
                println("Adiós!")
            }
            else -> println("Opción no válida, por favor intente de nuevo.")
        }

    } while (opcion != 0)
}