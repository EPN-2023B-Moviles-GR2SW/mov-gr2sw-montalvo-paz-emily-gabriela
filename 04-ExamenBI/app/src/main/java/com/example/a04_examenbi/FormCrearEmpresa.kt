package com.example.a04_examenbi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText

class FormCrearEmpresa : AppCompatActivity() {
    lateinit var adaptador: ArrayAdapter<String>;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_crear_empresa)
        cargarFormEmpresa();
        val botonGuardarEmpresa = findViewById<Button>(R.id.btn_guardar_empresa)
        botonGuardarEmpresa.setOnClickListener(){
            guardarEmpresa();
            finish();
        }
        val botonCancelar = findViewById<Button>(R.id.btn_cancelar_empresa)
        botonCancelar.setOnClickListener({finish()})
    }

    fun guardarEmpresa() {
        val nombreEmpresa =findViewById<EditText>(R.id.imput_nombre_empresa);
        val telefonoEmpresa =findViewById<EditText>(R.id.imput_telefono_empresa);
        val estadoEmpresa =findViewById<EditText>(R.id.imput_estado_empresa);
        val categoriaEmpresa =findViewById<EditText>(R.id.input_categoria_empresa);
        val empresaId = intent.getIntExtra("empresaId",-1);
        var empresa = BEmpresa(
            nombreEmpresa = nombreEmpresa.text.toString(),
            numeroCelular  = telefonoEmpresa.text.toString().toInt(),
             estado = estadoEmpresa.text.toString().toBoolean(),
            categoria = categoriaEmpresa.text.toString()
        )
        if(empresaId == -1){
            BaseDatosMemoria.crearEmpresa(empresa);
            setResult(RESULT_OK);
        }else{
            BaseDatosMemoria.actualizarEmpresa(empresaId,empresa);
            setResult(RESULT_OK);
        }
    }

    fun cargarFormEmpresa(){
        val nombreEmpresa =findViewById<EditText>(R.id.imput_nombre_empresa);
        val telefonoEmpresa =findViewById<EditText>(R.id.imput_telefono_empresa);
        val estadoEmpresa =findViewById<EditText>(R.id.imput_estado_empresa);
        val categoriaEmpresa =findViewById<EditText>(R.id.input_categoria_empresa);
        val empresaId = intent.getIntExtra("empresaId",-1);
        if(empresaId != -1){
            val empresa = BaseDatosMemoria.buscarEmpresa(empresaId);
            if(empresa!==null){
                nombreEmpresa.setText(empresa.nombreEmpresa.toString());
                telefonoEmpresa.setText(empresa.numeroCelular.toString());
                estadoEmpresa.setText(empresa.estado.toString());
                categoriaEmpresa.setText(empresa.categoria);
            }
        }

    }
}


