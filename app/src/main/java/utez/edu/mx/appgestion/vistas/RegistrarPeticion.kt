package utez.edu.mx.appgestion.vistas

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import utez.edu.mx.appgestion.R
import utez.edu.mx.appgestion.databinding.ActivityRegistrarPeticionBinding
import utez.edu.mx.appgestion.modelo.ReporteBean
import utez.edu.mx.appgestion.modelo.ReportePost
import utez.edu.mx.appgestion.utilidades.JsonPlaceHolderApi
import utez.edu.mx.appgestion.utilidades.RetrofitConexion
import java.text.SimpleDateFormat
import java.util.*

class RegistrarPeticion : AppCompatActivity() {
    lateinit var preferences: SharedPreferences
    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_peticion)
        //View Binding
        val binding = ActivityRegistrarPeticionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Shared Preferences
        preferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        var idEmp = preferences.getInt("IDEMPLEADO",0)
        //Hora del sistema Android
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val currentDateAndTime: String = simpleDateFormat.format(Date())
        binding.txtNombrePeticion.text = preferences.getString("NOMBRE","")
        binding.txtAreaPeticion.text = preferences.getString("AREA","")
        binding.txtFechaRegistroPeticion.text = currentDateAndTime
        binding.btnGuardarTodo.setOnClickListener {
            var descripcion = binding.txtDescripcionPeticion.text.toString()
            val jsonPlaceHolderApi = RetrofitConexion.getRetrofit().create(JsonPlaceHolderApi::class.java)
            val call:Call<Boolean> = jsonPlaceHolderApi.insertarReporte(ReportePost(0,"",descripcion,idEmp,0))
            call.enqueue(object :Callback<Boolean>{
                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    Log.e("ERROR",t.message.toString())
                }

                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    Toast.makeText(applicationContext,"Petición realizada con exito",Toast.LENGTH_LONG).show()
                    limpiar()
                }
            })
        }
        binding.btnRegresarRegistrarP.setOnClickListener {
            val intent = Intent(this,MenuEmpleados::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun limpiar(){
        val intent = Intent(this,RegistrarPeticion::class.java)
        startActivity(intent)
        finish()
    }
}