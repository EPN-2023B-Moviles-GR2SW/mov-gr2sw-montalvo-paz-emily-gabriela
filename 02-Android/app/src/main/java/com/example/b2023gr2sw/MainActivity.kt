package com.example.b2023gr2sw

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    val callbackContenidoIntenExplicito = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        result ->
        if (result.resultCode == Activity.RESULT_OK){
            if(result.data != null){
                //logica de negocio
                val data =  result.data
                "${data?.getStringExtra("nombreModificado")}"
            }
        }
    }

    val callbackIntentPickUri =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
                result ->
            if (result.resultCode == RESULT_OK){
                if(result.data != null){
                    if(result.data!!.data != null){
                        val uri: Uri = result.data!!.data!!
                        val cursor = contentResolver.query(
                            uri, null, null, null, null, null)
                        cursor?.moveToFirst()
                        val indiceTelefono = cursor?.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                        )
                        val telefono = cursor?.getString(indiceTelefono!!)
                        cursor?.close()
                        mostrarSnackbar("Telefono ${telefono}")

                    }

                    val data =  result.data
                    mostrarSnackbar("${data?.getStringExtra("nombreModificado")}")

                }
            }


        }

    fun mostrarSnackbar(texto:String){
        val snack = Snackbar.make(findViewById(R.id.id_layout_main), texto, Snackbar.LENGTH_LONG)
        snack.show()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Base de datos sqLite
        EBaseDeDatos.tablaEntrenador = ESqliteHelperEntrenator(this)

        val botonCicloVida = findViewById<Button>(R.id.btn_ciclo_vida)
        botonCicloVida.setOnClickListener{
            irActividad(ACicloVida::class.java)
        }

        val botonListView = findViewById<Button>(R.id.btn_ir_list_view)
        botonListView.setOnClickListener{
            irActividad(BListView::class.java)
        }

        val botonIntentImplicito = findViewById<Button>(R.id.btn_ir_intent_implicito)
        botonIntentImplicito.setOnClickListener {
            val intentConRespuesta = Intent(
                Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI
            )
            callbackIntentPickUri.launch(intentConRespuesta)
        }

        val botonIntentExplicito = findViewById<Button>(R.id.btn_ir_intent_explicito)
        botonIntentExplicito.setOnClickListener {
            abrirActividadConParametros(CIntentExplicitoParametros::class.java)
        }

        val botonSqlite = findViewById<Button>(R.id.btn_sqlite)
        botonSqlite.setOnClickListener {
            irActividad(ECrudEntrenador:: class.java)
        }

        val botonRView = findViewById<Button>(R.id.btn_recycler_view)
        botonRView.setOnClickListener {
            irActividad(ECrudEntrenador:: class.java)
        }

    }

    fun abrirActividadConParametros(clase: Class<*>) {
        val intentExplicito = Intent(this, clase)
        //Enviar parametros(solamente variables primitivas)
        intentExplicito.putExtra("nombre", "Adrian")
        intentExplicito.putExtra("apellido", "Eguez")
        intentExplicito.putExtra("edad", 34)
        callbackContenidoIntenExplicito.launch(intentExplicito)

    }

    fun irActividad(
        clase:Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}



