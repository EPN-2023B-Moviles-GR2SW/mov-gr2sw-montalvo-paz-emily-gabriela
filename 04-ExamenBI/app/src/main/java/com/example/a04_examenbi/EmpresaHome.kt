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
import com.example.a04_examenbi.BaseDatosMemoria.Companion.eliminarEmpresa

class EmpresaHome : AppCompatActivity() {

    lateinit var adaptador: ArrayAdapter<String>;
    var posicionEmpresaSeleccionada = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empresa_home)

        val botonCrearEmpresa = findViewById<Button>(R.id.btn_crear_empresa)
        botonCrearEmpresa.setOnClickListener {
            irActividad(FormCrearEmpresa::class.java)
        }
        actualizarListaEmpresas();

    }

    private fun actualizarListaEmpresas() {
        val listViewEmpresas = findViewById<ListView>(R.id.lv_lista_empresa)
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            BaseDatosMemoria.empresas.mapIndexed { index, empresa ->
                "${empresa.nombreEmpresa}"
            }
        )
        listViewEmpresas.adapter = adaptador;
        adaptador.notifyDataSetChanged();
        registerForContextMenu(listViewEmpresas)
    }
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_empresa, menu)
        val informacion = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = informacion.position
        posicionEmpresaSeleccionada = posicion
    }


    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_ver_empleados -> {
                val empresaId = posicionEmpresaSeleccionada
                irActividad(EmpleadoHome::class.java, empresaId)
                true
            }
            R.id.mi_editar_empresa -> {
                val empresaId = posicionEmpresaSeleccionada
                irActividad(FormCrearEmpresa::class.java, empresaId)
                true
            }
            R.id.mi_eliminar_empresa -> {
                eliminar();
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun eliminar() {
        val empresaSeleccionada = BaseDatosMemoria.empresas.getOrNull(posicionEmpresaSeleccionada)
        if (empresaSeleccionada != null) {
            val mensajeEliminar = AlertDialog.Builder(this)
            mensajeEliminar.setTitle("¿Desea eliminar la empresa ${empresaSeleccionada.nombreEmpresa}?")
            mensajeEliminar.setNegativeButton("Cancelar", null)
            mensajeEliminar.setPositiveButton("Eliminar") { _, _ ->
                if (BaseDatosMemoria.eliminarEmpresa(posicionEmpresaSeleccionada)) {
                    actualizarListaEmpresas()
                }
            }
            val dialog = mensajeEliminar.create()
            dialog.show()
        }
    }

    fun irActividad(clase: Class<*>, empresaId: Int = -1) {
        val intent = Intent(this, clase)
        intent.putExtra("empresaId", empresaId)
        startActivity(intent)
    }
    override fun onRestart() {
        super.onRestart()
        actualizarListaEmpresas()
    }

    override fun onResume() {
        super.onResume()
        actualizarListaEmpresas()
    }

}