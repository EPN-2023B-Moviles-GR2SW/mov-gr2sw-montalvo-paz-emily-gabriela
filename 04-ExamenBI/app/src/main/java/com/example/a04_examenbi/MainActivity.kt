package com.example.a04_examenbi

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.a04_examenbi.ui.theme._04ExamenBITheme

class MainActivity : ComponentActivity() {

    lateinit var adaptador: ArrayAdapter<String>;
    var posicionItemSeleccionado = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empresa_home)

        val botonCrearEmpresa = findViewById<Button>(R.id.btn_crear_empresa)
        botonCrearEmpresa.setOnClickListener { FormCrearEmpresa::class.java  }
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


    fun irActividad(clase: Class<*>, empresaId: Int = -1) {
        val intent = Intent(this, clase)
        intent.putExtra("empresaId", empresaId)
        startActivity(intent)
    }

}
