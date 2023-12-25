package modelo.entidades
import java.io.Serializable

class Empresa(
    var nombreEmpresa: String,
    var numeroEmpleados: Int,
    var ingresoAnual: Double,
    var ubicacion: String,
    var esInternacional: Boolean,
    var listaEmpleados: MutableList<Empleado> = mutableListOf()
) : Serializable

