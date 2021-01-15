package utez.edu.mx.appgestion.vistas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import utez.edu.mx.appgestion.R
import utez.edu.mx.appgestion.databinding.ActivityRegistrarEmpleadoBinding
import utez.edu.mx.appgestion.modelo.EmpleadoPost
import utez.edu.mx.appgestion.utilidades.JsonPlaceHolderApi
import utez.edu.mx.appgestion.utilidades.RetrofitConexion
import java.security.AccessControlContext

class RegistrarEmpleado : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_empleado)

        //View Binding
        val binding = ActivityRegistrarEmpleadoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Creación de los Spinners de sexos y puestos
        ArrayAdapter.createFromResource(
                this,
                R.array.sexospinner,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spSexoRegistro.adapter = adapter
        }

        ArrayAdapter.createFromResource(
                this,
                R.array.puestospinner,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spPuestoRegistro.adapter = adapter
        }

        //Boton para registrar empleado
        binding.btnRegistrarEmpleado.setOnClickListener {
            //Variables necesarias para enviar al POST
            var nombre = binding.tctNombreRegistro.text.toString()
            var domicilio = binding.txtDomicilioRegistro.text.toString()
            var sexo = binding.spSexoRegistro.selectedItem.toString()
            var fechaNacimiento = binding.txtFechaNacimiento.text.toString()
            var email = binding.txtEmailRegistro.text.toString()
            var telefono = binding.txtTelefonoRegistro.text.toString()
            var area = binding.txtAreaRegistro.text.toString()
            var puesto = binding.spPuestoRegistro.selectedItem.toString()
            //Objeto de Retrofit
            val jsonPlaceHolderApi = RetrofitConexion.getRetrofit().create(JsonPlaceHolderApi::class.java)
            val call:Call<Boolean>
            if (puesto.equals("Empleado")){
                call = jsonPlaceHolderApi.insertarEmpleado(EmpleadoPost(0,nombre,domicilio,sexo,fechaNacimiento,email,telefono,email,area,2))
                call.enqueue(object :Callback<Boolean>{
                    override fun onFailure(call: Call<Boolean>, t: Throwable) {
                        Log.e("ERROR",t.message.toString())
                    }
                    override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                        Toast.makeText(applicationContext,"Registrado correctamente",Toast.LENGTH_LONG).show()
                        limpiar()
                    }
                })
            }else if (puesto.equals("Personal De Servicios")){
                call = jsonPlaceHolderApi.insertarEmpleado(EmpleadoPost(0,nombre,domicilio,sexo,fechaNacimiento,email,telefono,email,area,3))
                call.enqueue(object :Callback<Boolean>{
                    override fun onFailure(call: Call<Boolean>, t: Throwable) {
                        Log.e("ERROR",t.message.toString())
                    }
                    override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                        Toast.makeText(applicationContext,"Registrado correctamente",Toast.LENGTH_LONG).show()
                        limpiar()
                    }
                })
            }else{
                Toast.makeText(applicationContext,"Seleccione un puesto valido",Toast.LENGTH_LONG).show()
            }
        }

        binding.btnRegresarPerfil.setOnClickListener {
            val intent = Intent(this,MenuAdministrador::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun limpiar(){
        val intent = Intent(this,RegistrarEmpleado::class.java)
        startActivity(intent)
        finish()
    }
}