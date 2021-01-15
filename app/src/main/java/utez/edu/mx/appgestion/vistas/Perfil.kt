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
import utez.edu.mx.appgestion.R
import utez.edu.mx.appgestion.databinding.ActivityPerfilBinding
import utez.edu.mx.appgestion.modelo.EmpleadoPost
import utez.edu.mx.appgestion.utilidades.JsonPlaceHolderApi
import utez.edu.mx.appgestion.utilidades.RetrofitConexion

class Perfil : AppCompatActivity() {
    lateinit var preferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)
        //View Binding
        val binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Shared Preferences
        preferences = getSharedPreferences("SHARED_PREF",Context.MODE_PRIVATE)
        //Variable donde controlo los atributos
        var idRol = preferences.getInt("IDROL",0)
        var nombreCompleto = preferences.getString("NOMBRE","")
        var domicilio = preferences.getString("DOMICILIO","")
        var sexo = preferences.getString("SEXO","")
        var fechaNacimiento = preferences.getString("FECHANACIMIENTO","")
        var email = preferences.getString("EMAIL","")
        var telefono = preferences.getString("TELEFONO","")
        var area = preferences.getString("AREA","")
        var contraDefault = preferences.getString("CONTRA","")
        var idEmp = preferences.getInt("IDEMPLEADO",0)
        //Llenar los textView
        binding.txtNombre.setText(nombreCompleto)
        binding.txtDomicilio.setText(domicilio)
        binding.txtSexo.text = sexo
        binding.txtFechaNacimiento.text = fechaNacimiento
        binding.txtEmail.setText(email)
        binding.txtTelefono.setText(telefono)
        binding.txtArea.text = area
        if (idRol==1){
            binding.txtPuesto.text = "Administrador"
        }else if(idRol==2){
            binding.txtPuesto.text = "Empleado"
        }else if (idRol==3){
            binding.txtPuesto.text = "Personal De Servicios"
        }

        binding.btnGuardarTodo.setOnClickListener {
            //Variables necesarias para enviar al POST
            var nombre2 = binding.txtNombre.text.toString()
            var domicilio2 = binding.txtDomicilio.text.toString()
            var email2 = binding.txtEmail.text.toString()
            var telefono2 = binding.txtTelefono.text.toString()
            var contraNueva = binding.txtNuevaContrasenia.text.toString()
            //Objeto de Retrofit
            val jsonPlaceHolderApi = RetrofitConexion.getRetrofit().create(JsonPlaceHolderApi::class.java)
            if (contraNueva==""){
                if (contraDefault!=null){
                    val call:Call<Boolean> = jsonPlaceHolderApi.actualizarEmpleado(EmpleadoPost(idEmp,nombre2,domicilio2,"","",email2,telefono2,contraDefault,"",0))
                    call.enqueue(object :Callback<Boolean>{
                        override fun onFailure(call: Call<Boolean>, t: Throwable) {
                            Log.e("ERROR",t.message.toString())
                        }

                        override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                            Toast.makeText(applicationContext,"Actualizado correctamente, cierre sesión para ver los cambios",Toast.LENGTH_LONG).show()
                        }

                    })
                }
            }else if (contraNueva!=""){
                val call:Call<Boolean> = jsonPlaceHolderApi.actualizarEmpleado(EmpleadoPost(idEmp,nombre2,domicilio2,"","",email2,telefono2,contraNueva,"",0))
                call.enqueue(object :Callback<Boolean>{
                    override fun onFailure(call: Call<Boolean>, t: Throwable) {
                        Log.e("ERROR",t.message.toString())
                    }

                    override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                        Toast.makeText(applicationContext,"Actualizado correctamente",Toast.LENGTH_LONG).show()
                    }

                })
            }

        }


        binding.btnRegresarPerfil.setOnClickListener {
            if (idRol==1){
                val intent = Intent(this,MenuAdministrador::class.java)
                startActivity(intent)
                finish()
            }else if(idRol==2){
                val intent = Intent(this,MenuEmpleados::class.java)
                startActivity(intent)
                finish()
            }else if(idRol==3){
                val intent = Intent(this,MenuPersonal::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}