package com.example.a04_examenbi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class FormAgregarEmpleado : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_agregar_empleado)

        llenarListaEmpleados();

        val botonGuardarEmpleado = findViewById<Button>(R.id.btn_guardar_empleado)
        botonGuardarEmpleado.setOnClickListener{
            guardarEmpleado();
            finish()
          }

        val botonCancelarEmpleado = findViewById<Button>(R.id.btn_calcelar_empleado)
        botonCancelarEmpleado.setOnClickListener({finish()})
    }

    fun guardarEmpleado() {
        val nombreEmpleado = findViewById<EditText>(R.id.input_nombre_empleado).text.toString()
        val apellidoEmpleado = findViewById<EditText>(R.id.input_apellido_empleado).text.toString()
        val edadEmpleadoText = findViewById<EditText>(R.id.input_edad_empleado).text.toString()
        val tiempoCompletoText = findViewById<EditText>(R.id.input_jornada_empleado).text.toString()

        if (nombreEmpleado.isNotBlank() && apellidoEmpleado.isNotBlank() && edadEmpleadoText.isNotBlank() && tiempoCompletoText.isNotBlank()) {
            val edadEmpleado = edadEmpleadoText.toIntOrNull()
            val tiempoCompleto = tiempoCompletoText.toBoolean()

            if (edadEmpleado != null) {
                val empleado = BEmpleado(
                    nombreEmpleado = nombreEmpleado,
                    apellidoEmpleado = apellidoEmpleado,
                    edadEmpleado = edadEmpleado,
                    tiempoCompleto = tiempoCompleto
                )

                val empresaId = intent.getIntExtra("empresaId", -1)
                if (empresaId != -1) {
                    BaseDatosMemoria.agregarEmpleado(empresaId.toString(), empleado) { success, error ->
                        if (success) {
                            setResult(RESULT_OK)
                            finish()
                        } else {
                            // Manejar error al agregar empleado
                        }
                    }
                } else {
                    // Manejar error, no se proporcionó empresaId
                }
            } else {
                // Manejar error, la edad no es un número válido
            }
        } else {
            // Manejar error, algún campo está vacío
        }
    }


    fun llenarListaEmpleados() {
        val nombreEmpleadoEditText = findViewById<EditText>(R.id.input_nombre_empleado)
        val apellidoEmpleadoEditText = findViewById<EditText>(R.id.input_apellido_empleado)
        val edadEmpleadoEditText = findViewById<EditText>(R.id.input_edad_empleado)
        val tiempoCompletoEditText = findViewById<EditText>(R.id.input_jornada_empleado)

        val empresaId = intent.getIntExtra("empresaId", -1)
        val empleadoId = intent.getIntExtra("empleadoId", -1)

        if (empresaId != -1 && empleadoId != -1) {
            BaseDatosMemoria.buscarEmpresa(empresaId.toString()) { empresa ->
                if (empresa != null) {
                    val empleado = empresa.empleados.getOrNull(empleadoId)
                    if (empleado != null) {
                        nombreEmpleadoEditText.setText(empleado.nombreEmpleado)
                        apellidoEmpleadoEditText.setText(empleado.apellidoEmpleado)
                        edadEmpleadoEditText.setText(empleado.edadEmpleado.toString())
                        tiempoCompletoEditText.setText(empleado.tiempoCompleto.toString())
                    } else {
                        // Manejar error, empleado no encontrado
                    }
                } else {
                    // Manejar error, empresa no encontrada
                }
            }
        } else {
            // Manejar error, empresaId o empleadoId no proporcionados
        }
    }


}