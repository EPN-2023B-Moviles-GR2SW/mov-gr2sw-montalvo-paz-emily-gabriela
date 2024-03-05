import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.a04_examenbi.BEmpresa
import com.example.a04_examenbi.BaseDatosMemoria
import com.example.a04_examenbi.R

class FormCrearEmpresa : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_crear_empresa)

        cargarFormEmpresa()

        val botonGuardarEmpresa = findViewById<Button>(R.id.btn_guardar_empresa)
        botonGuardarEmpresa.setOnClickListener {
            guardarEmpresa()
        }

        val botonCancelar = findViewById<Button>(R.id.btn_cancelar_empresa)
        botonCancelar.setOnClickListener {
            finish()
        }
    }

    private fun guardarEmpresa() {
        val nombreEmpresa = findViewById<EditText>(R.id.imput_nombre_empresa).text.toString()
        val telefonoEmpresa = findViewById<EditText>(R.id.imput_telefono_empresa).text.toString().toIntOrNull()
        val estadoEmpresa = findViewById<EditText>(R.id.imput_estado_empresa).text.toString()
        val categoriaEmpresa = findViewById<EditText>(R.id.input_categoria_empresa).text.toString()

        if (nombreEmpresa.isNotBlank() && telefonoEmpresa != null && estadoEmpresa != null) {
            val empresaId = intent.getIntExtra("empresaId", -1)
            val empresa = BEmpresa(
                nombreEmpresa = nombreEmpresa,
                numeroCelular = telefonoEmpresa,
                estado = estadoEmpresa,
                categoria = categoriaEmpresa
            )

            if (empresaId == -1) {
                BaseDatosMemoria.crearEmpresa(empresa)
            } else {
                BaseDatosMemoria.actualizarEmpresa(empresaId.toString(), empresa)
            }

            setResult(RESULT_OK)
            finish()
        } else {
            // Manejar error, algún campo está vacío o los datos son inválidos
        }
    }

    private fun cargarFormEmpresa() {
        val nombreEmpresa = findViewById<EditText>(R.id.imput_nombre_empresa)
        val telefonoEmpresa = findViewById<EditText>(R.id.imput_telefono_empresa)
        val estadoEmpresa = findViewById<EditText>(R.id.imput_estado_empresa)
        val categoriaEmpresa = findViewById<EditText>(R.id.input_categoria_empresa)

        val empresaId = intent.getIntExtra("empresaId", -1)
        if (empresaId != -1) {
            BaseDatosMemoria.buscarEmpresa(empresaId.toString()) { empresa ->
                if (empresa != null) {
                    nombreEmpresa.setText(empresa.nombreEmpresa ?: "")
                    telefonoEmpresa.setText(empresa.numeroCelular?.toString() ?: "")
                    estadoEmpresa.setText(empresa.estado?.toString() ?: "")
                    categoriaEmpresa.setText(empresa.categoria)
                } else {
                    // Manejar error, empresa no encontrada
                }
            }
        }
    }
}
