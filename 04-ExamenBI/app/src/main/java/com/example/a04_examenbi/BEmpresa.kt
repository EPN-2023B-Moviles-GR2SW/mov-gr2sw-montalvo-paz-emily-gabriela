package com.example.a04_examenbi

data class BEmpresa (
    var nombreEmpresa: String?,
    var numeroCelular: Int?,
    var estado: Boolean?,
    var categoria: String,
    var empleados: MutableList<BEmpleado> = mutableListOf()
)