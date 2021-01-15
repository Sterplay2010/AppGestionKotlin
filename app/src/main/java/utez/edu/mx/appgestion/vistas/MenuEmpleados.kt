package utez.edu.mx.appgestion.vistas

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import utez.edu.mx.appgestion.R
import utez.edu.mx.appgestion.databinding.ActivityMenuEmpleadosBinding

class MenuEmpleados : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_empleados)
        sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        //View Binding
        val binding = ActivityMenuEmpleadosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegistrarPeticion.setOnClickListener {
            val intent = Intent(this, RegistrarPeticion::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnConsultarEmpleado.setOnClickListener {
            val intent = Intent(this, ReporteHistorialEmpleados::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnPerfilEmpleado.setOnClickListener {
            val intent = Intent(this, Perfil::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnCerrarSesionEmpleado.setOnClickListener {
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