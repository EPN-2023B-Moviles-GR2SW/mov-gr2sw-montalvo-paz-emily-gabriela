package controlador

import modelo.EmpresaManager
import modelo.EmpresaMenu
import modelo.entidades.Empresa

fun main() {
    val empresas = mutableListOf<Empresa>()
    val empresaManager = EmpresaManager(empresas)
    val empresaMenu = EmpresaMenu(empresaManager)

    do {
        empresaMenu.mostrarMenu()
        val opcion = readLine()?.toIntOrNull()

        when (opcion) {
            0 -> println("Adiós!")
            else -> println("Opción no válida, por favor intente de nuevo.")
        }

    } while (opcion != 0)
}