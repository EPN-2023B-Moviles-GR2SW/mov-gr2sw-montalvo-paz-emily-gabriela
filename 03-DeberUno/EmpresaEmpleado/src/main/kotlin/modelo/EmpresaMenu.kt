package modelo

class EmpresaMenu(private val empresaManager: EmpresaManager) {

    fun mostrarMenu() {
        do {
            println("MENÚ:")
            println("*****************************************")
            println("1. Listar Empresas")
            println("2. Crear Nueva Empresa")
            println("0. Salir")
            println("*****************************************")
            print("Seleccione una opción: ")

            val opcion = readLine()?.toIntOrNull()

            when (opcion) {
                1 -> empresaManager.listarEmpresas()
                2 -> empresaManager.crearNuevaEmpresa()
                0 -> println("Adiós!")
                else -> println("Opción no válida, por favor intente de nuevo.")
            }
        } while (opcion!= 0)
    }
}