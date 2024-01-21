package com.example.a04_examenbi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog

class EmpleadoHome : AppCompatActivity() {
    lateinit var adaptador: ArrayAdapter<String>;
    lateinit var empleados:MutableList<BEmpleado>;
    private  var posicionEmpleadoSeleccionado = -1
    var empresaId =-1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empleado_home)

        val botonAgregarEmpleado = findViewById<Button>(R.id.btn_agregar_empleado)
        botonAgregarEmpleado.setOnClickListener{
            irActividad(FormAgregarEmpleado::class.java)
        }

        empresaId = intent.getIntExtra("empresaId",-1)

        actualizarListaEmpleados();
    }
    private fun actualizarListaEmpleados() {
        var empresa =  BaseDatosMemoria.buscarEmpresa(empresaId)!!;
        empleados = BaseDatosMemoria.cargarEmpleados(empresa);
        val listaEmpleados = findViewById<ListView>(R.id.lv_empleados)
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            empleados.mapIndexed { index, empleado ->
                "${empleado.nombreEmpleado}"
            }
        )
        listaEmpleados.adapter = adaptador;
        adaptador.notifyDataSetChanged();
        registerForContextMenu(listaEmpleados);
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        var inflater = menuInflater
        inflater.inflate(R.menu.menu_empleados, menu)
        val informacionEmpleado = menuInfo as AdapterView.AdapterContextMenuInfo;
        val posicionEmpleado = informacionEmpleado.position;
        if(posicionEmpleado != null){
            posicionEmpleadoSeleccionado = posicionEmpleado;
        }

    }
    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.mi_editar_empleado->{
                try {
                    val empleadoId = posicionEmpleadoSeleccionado;
                    irActividad(FormAgregarEmpleado::class.java, empleadoId, empresaId)
                } catch (e: Throwable){}
                true;
            }
            R.id.mi_eliminar_empleado ->{
                eliminar()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun irActividad(clase:Class<*>){
        val intent = Intent(this,clase);
        intent.putExtra("empresaId",empresaId);
        actualizarListaEmpleados()
        startActivity(intent)
    }

    fun irActividad(clase:Class<*>,empleadoId:Int?,empresaId: Int?) {
        val intent = Intent(this, clase)
        intent.putExtra("empleadoId", empleadoId)
        intent.putExtra("empresaId", empresaId)
        startActivity(intent)
    }

    fun eliminar(){
        val builderDialog = AlertDialog.Builder(this)
        builderDialog.setTitle("¿Deseas eliminar al empleado?")
        builderDialog.setNegativeButton("Cancelar",null);
        builderDialog.setPositiveButton("Eliminar"){
                dialog,_ ->
            if(posicionEmpleadoSeleccionado.let { BaseDatosMemoria.eliminarEmpleado(empresaId, it) }){
                actualizarListaEmpleados()
            }
        }
        val dialog = builderDialog.create();
        dialog.show();
    }

    override fun onRestart() {
        super.onRestart()
        actualizarListaEmpleados()
    }

    override fun onResume() {
        super.onResume()
        actualizarListaEmpleados()
    }

}