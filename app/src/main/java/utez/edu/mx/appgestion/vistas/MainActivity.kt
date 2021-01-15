package utez.edu.mx.appgestion.vistas

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import utez.edu.mx.appgestion.R
import utez.edu.mx.appgestion.databinding.ActivityMainBinding
import utez.edu.mx.appgestion.modelo.EmpleadoBean
import utez.edu.mx.appgestion.utilidades.JsonPlaceHolderApi
import utez.edu.mx.appgestion.utilidades.RetrofitConexion

class MainActivity : AppCompatActivity() {
    //Inicio de sesión
    lateinit var sharedPreferences: SharedPreferences
    var recordar = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Shared Prerences
        sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        recordar = sharedPreferences.getBoolean("RECORDAR", false)
        var idRol = sharedPreferences.getInt("IDROL", 0)

        //View Binding
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Esto me valida si la sesión ya esta activa
        if (recordar) {
            if (idRol == 1) {
                val intent = Intent(this, MenuAdministrador::class.java)
                startActivity(intent)
                finish()
            } else if (idRol == 2) {
                val intent = Intent(this, MenuEmpleados::class.java)
                startActivity(intent)
                finish()
            } else if (idRol == 3) {
                val intent = Intent(this, MenuPersonal::class.java)
                startActivity(intent)
                finish()
            }
        }

        binding.btnIniciarSesion.setOnClickListener {
            //Objeto de Retrofit
            var email = binding.txtEmail.text.toString()
            var contra = binding.txtContrasenia.text.toString()
            var checar = binding.recordar.isChecked
            val jsonPlaceHolderApi = RetrofitConexion.getRetrofit().create(JsonPlaceHolderApi::class.java)
            val call: Call<EmpleadoBean> = jsonPlaceHolderApi.iniciarSesion(email)
            call.enqueue(object : Callback<EmpleadoBean> {
                override fun onFailure(call: Call<EmpleadoBean>, t: Throwable) {
                    Log.e("Error", t.message.toString())
                }

                override fun onResponse(call: Call<EmpleadoBean>, response: Response<EmpleadoBean>) {
                    var idEmp = response.body()?.idEmp
                    var nombreEmpleado = response.body()?.nombreCompleto
                    var domicilio = response.body()?.domicilio
                    var sexo = response.body()?.sexo
                    var fechaNacimiento = response.body()?.fechaNacimiento
                    var emailEmp = response.body()?.email
                    var telefono = response.body()?.telefono
                    var contraEmpleado = response.body()?.contrasenia
                    var area = response.body()?.area
                    var idRol = response.body()?.idRol
                    Log.d("emailEmpleado",emailEmp.toString())
                    validar(contra, email, checar, idEmp, nombreEmpleado, domicilio, sexo, fechaNacimiento, emailEmp, telefono, contraEmpleado, area, idRol)
                }
            })
        }
    }

    //funcion que valida el correo y la contraseña y envia a su respectiva vista
    fun validar(contratxt: String, emailtxt: String, checar: Boolean, idEmp: Int?, nombreEmpleado: String?, domicilio: String?, sexo: String?, fechaNacimiento: String?, email: String?, telefono: String?, contraEmpleado: String?, area: String?, idRol: Int?) {
        if (contraEmpleado == contratxt && email == emailtxt) {
            if (idRol == 1) {
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putBoolean("RECORDAR", checar)
                if (idEmp != null) {
                    editor.putInt("IDEMPLEADO", idEmp)
                }
                editor.putString("NOMBRE", nombreEmpleado)
                editor.putString("DOMICILIO", domicilio)
                editor.putString("SEXO", sexo)
                editor.putString("FECHANACIMIENTO", fechaNacimiento)
                editor.putString("EMAIL", email)
                editor.putString("CONTRA", contraEmpleado)
                editor.putString("TELEFONO", telefono)
                editor.putString("AREA", area)
                editor.putInt("IDROL", idRol)
                editor.apply()
                val intent = Intent(this, MenuAdministrador::class.java)
                startActivity(intent)
                finish()
            } else if (idRol == 2) {
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putBoolean("RECORDAR", checar)
                if (idEmp != null) {
                    editor.putInt("IDEMPLEADO", idEmp)
                }
                editor.putString("NOMBRE", nombreEmpleado)
                editor.putString("DOMICILIO", domicilio)
                editor.putString("SEXO", sexo)
                editor.putString("FECHANACIMIENTO", fechaNacimiento)
                editor.putString("EMAIL", email)
                editor.putString("CONTRA", contraEmpleado)
                editor.putString("TELEFONO", telefono)
                editor.putString("AREA", area)
                editor.putInt("IDROL", idRol)
                editor.apply()
                val intent = Intent(this, MenuEmpleados::class.java)
                startActivity(intent)
                finish()
            } else if (idRol == 3) {
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putBoolean("RECORDAR", checar)
                if (idEmp != null) {
                    editor.putInt("IDEMPLEADO", idEmp)
                }
                editor.putString("NOMBRE", nombreEmpleado)
                editor.putString("DOMICILIO", domicilio)
                editor.putString("SEXO", sexo)
                editor.putString("FECHANACIMIENTO", fechaNacimiento)
                editor.putString("EMAIL", email)
                editor.putString("CONTRA", contraEmpleado)
                editor.putString("TELEFONO", telefono)
                editor.putString("AREA", area)
                editor.putInt("IDROL", idRol)
                editor.apply()
                val intent = Intent(this, MenuPersonal::class.java)
                startActivity(intent)
                finish()
            }
        } else {
            Toast.makeText(applicationContext, "Error, revise bien sus datos", Toast.LENGTH_LONG).show()
        }
    }
}