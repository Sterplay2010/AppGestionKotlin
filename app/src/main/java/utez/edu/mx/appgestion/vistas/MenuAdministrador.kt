package utez.edu.mx.appgestion.vistas

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import utez.edu.mx.appgestion.R
import utez.edu.mx.appgestion.databinding.ActivityMenuAdministradorBinding

class MenuAdministrador : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_administrador)
        sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        //View Binding
        val binding = ActivityMenuAdministradorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Funciones para el menu
        binding.btnAgregarEmpleado.setOnClickListener {
            val intent = Intent(this,RegistrarEmpleado::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnConsultarEmpleado.setOnClickListener {
            val intent = Intent(this,ConsultarEmpleado::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnPerfilAdmin.setOnClickListener {
            val intent = Intent(this,Perfil::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnCerrarSesionAdmin.setOnClickListener {
            //Borrar los valores del Shared Preferences
            val editor : SharedPreferences.Editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()
            //Salir de la sesión
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}