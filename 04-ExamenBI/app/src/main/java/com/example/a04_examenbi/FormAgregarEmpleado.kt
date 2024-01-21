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
        val nombreEmpleado = findViewById<EditText>(R.id.input_nombre_empleado)
        val apellidoEmpleado = findViewById<EditText>(R.id.input_apellido_empleado)
        val edadEmpleado = findViewById<EditText>(R.id.input_edad_empleado)
        val tiempoCompleto = findViewById<EditText>(R.id.input_jornada_empleado)
        var empleado = BEmpleado(
            nombreEmpleado = nombreEmpleado.text.toString(),
            apellidoEmpleado = apellidoEmpleado.text.toString(),
            edadEmpleado = edadEmpleado.text.toString().toInt(),
            tiempoCompleto  = tiempoCompleto.text.toString().toBoolean()
        )

        val empresaId = intent.getIntExtra("empresaId",-1);
        val empleadoId = intent.getIntExtra("empleadoId",-1);

        if(empresaId != -1 && empleadoId !=-1){
            BaseDatosMemoria.actualizarEmpleado(empresaId,empleadoId, empleado);
        }else{
            BaseDatosMemoria.agregarEmpleado(empresaId,empleado)
        }
        setResult(RESULT_OK);
    }

    fun llenarListaEmpleados() {
        val nombreEmpleado = findViewById<EditText>(R.id.input_nombre_empleado)
        val apellidoEmpleado = findViewById<EditText>(R.id.input_apellido_empleado)
        val edadEmpleado = findViewById<EditText>(R.id.input_edad_empleado)
        val tiempoCompleto = findViewById<EditText>(R.id.input_jornada_empleado)
        val empresaId = intent.getIntExtra("empresaId",-1);
        val empleadoId = intent.getIntExtra("empleadoId",-1);

        if(empresaId != -1 && empleadoId !=-1){
            val empresa  = BaseDatosMemoria.buscarEmpresa(empresaId);
            val empleado = empresa!!.empleados.get(empleadoId);
            nombreEmpleado.setText(empleado.nombreEmpleado);
            apellidoEmpleado.setText(empleado.apellidoEmpleado);
            edadEmpleado.setText(empleado.edadEmpleado.toString())
            tiempoCompleto .setText(empleado.tiempoCompleto.toString())
            }
    }
}