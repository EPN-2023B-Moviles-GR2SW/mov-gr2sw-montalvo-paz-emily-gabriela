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
import com.example.a04_examenbi.BEmpleado
import com.example.a04_examenbi.BaseDatosMemoria
import com.example.a04_examenbi.FormAgregarEmpleado
import com.example.a04_examenbi.R

class EmpleadoHome : AppCompatActivity() {
    lateinit var adaptador: ArrayAdapter<String>
    lateinit var empleados: MutableList<BEmpleado>
    private var posicionEmpleadoSeleccionado = -1
    var empresaId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empleado_home)

        val botonAgregarEmpleado = findViewById<Button>(R.id.btn_agregar_empleado)
        botonAgregarEmpleado.setOnClickListener {
            irActividad(FormAgregarEmpleado::class.java)
        }

        empresaId = intent.getIntExtra("empresaId", -1)

        actualizarListaEmpleados()
    }

    private fun actualizarListaEmpleados() {
        BaseDatosMemoria.buscarEmpresa(empresaId.toString()) { empresa ->
            if (empresa != null) {
                BaseDatosMemoria.cargarEmpleados(empresaId.toString()) { listaEmpleados, error ->
                    if (error == null) {
                        empleados = (listaEmpleados ?: mutableListOf()).toMutableList()
                        val listaEmpleadosView = findViewById<ListView>(R.id.lv_empleados)
                        adaptador = ArrayAdapter(
                            this,
                            android.R.layout.simple_list_item_1,
                            empleados.map { it.nombreEmpleado }
                        )
                        listaEmpleadosView.adapter = adaptador
                        adaptador.notifyDataSetChanged()
                        registerForContextMenu(listaEmpleadosView)
                    } else {
                        // Manejar error al cargar empleados
                    }
                }
            } else {
                // Manejar empresa no encontrada
            }
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_empleados, menu)
        val informacionEmpleado = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicionEmpleado = informacionEmpleado.position
        posicionEmpleadoSeleccionado = posicionEmpleado
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.mi_editar_empleado->{
                val empleadoId = empleados[posicionEmpleadoSeleccionado].id // Suponiendo que BEmpleado tiene un atributo id
                irActividad(FormAgregarEmpleado::class.java, empleadoId.toString(), empresaId.toString())
                true
            }
            R.id.mi_eliminar_empleado ->{
                eliminar()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun irActividad(clase:Class<*>, empleadoId:String, empresaId: String) {
        val intent = Intent(this, clase)
        intent.putExtra("empleadoId", empleadoId)
        intent.putExtra("empresaId", empresaId)
        startActivity(intent)
    }

    fun eliminar(){
        val builderDialog = AlertDialog.Builder(this)
        builderDialog.setTitle("¿Deseas eliminar al empleado?")
        builderDialog.setNegativeButton("Cancelar",null);
        builderDialog.setPositiveButton("Eliminar") { dialog, _ ->
            if (posicionEmpleadoSeleccionado != -1) {
                BaseDatosMemoria.eliminarEmpleado(empresaId.toString(), empleados[posicionEmpleadoSeleccionado].id.toString()) { success, error ->
                    if (success) {
                        actualizarListaEmpleados()
                    } else {
                        // Manejar error al eliminar empleado
                    }
                }
            }
        }
        val dialog = builderDialog.create()
        dialog.show()
    }

    fun irActividad(clase:Class<*>){
        val intent = Intent(this,clase);
        intent.putExtra("empresaId",empresaId);
        actualizarListaEmpleados()
        startActivity(intent)
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
