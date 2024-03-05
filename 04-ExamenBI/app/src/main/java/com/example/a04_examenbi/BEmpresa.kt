package com.example.a04_examenbi

data class BEmpresa(
    var id: String? = null,
    var nombreEmpresa: String?,
    var numeroCelular: Int?,
    var estado: String,
    var categoria: String,
    var empleados: MutableList<BEmpleado> = mutableListOf()
)