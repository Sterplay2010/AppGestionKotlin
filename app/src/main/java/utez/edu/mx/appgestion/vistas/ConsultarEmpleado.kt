package utez.edu.mx.appgestion.vistas

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import utez.edu.mx.appgestion.R
import utez.edu.mx.appgestion.adaptadores.AdaptadorEmpleado
import utez.edu.mx.appgestion.databinding.ActivityConsultarEmpleadoBinding
import utez.edu.mx.appgestion.modelo.EmpleadoBean
import utez.edu.mx.appgestion.utilidades.JsonPlaceHolderApi
import utez.edu.mx.appgestion.utilidades.RetrofitConexion

class ConsultarEmpleado : AppCompatActivity() {
    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consultar_empleado)

        //View Binding
        val binding = ActivityConsultarEmpleadoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Creación del RyclerView
        val recyclerView:RecyclerView=findViewById(R.id.rclvEmpleados)
        recyclerView.layoutManager=LinearLayoutManager(this,LinearLayout.VERTICAL,false)


        //Objeto de Retrofit
        val jsonPlaceHolderApi = RetrofitConexion.getRetrofit().create(JsonPlaceHolderApi::class.java)
        val call:Call<ArrayList<EmpleadoBean>> = jsonPlaceHolderApi.consultarEmpleados()
        call.enqueue(object :Callback<ArrayList<EmpleadoBean>>{
            override fun onFailure(call: Call<ArrayList<EmpleadoBean>>, t: Throwable) {
                Log.e("ERROR",t.message.toString())
            }

            override fun onResponse(call: Call<ArrayList<EmpleadoBean>>, response: Response<ArrayList<EmpleadoBean>>) {
                val Empleados:ArrayList<EmpleadoBean> = response.body()!!
                val listaEmp = ArrayList<EmpleadoBean>()
                for (empleado in Empleados){
                    var idEmp = empleado.idEmp
                    var nombreCompleto = empleado.nombreCompleto
                    var domicilio = empleado.domicilio
                    var sexo = empleado.sexo
                    var fechaNacimiento = empleado.fechaNacimiento
                    var email = empleado.email
                    var telefono = empleado.telefono
                    var contrasenia = empleado.contrasenia
                    var area = empleado.area
                    var idRol = empleado.idRol
                    listaEmp.add(EmpleadoBean(idEmp,nombreCompleto,domicilio,sexo,fechaNacimiento,email,telefono,contrasenia,area,idRol))
                }
                val adapter = AdaptadorEmpleado(listaEmp)
                recyclerView.adapter = adapter
            }
        })

        //Regresar al menu
        binding.btnRegresarAtenderP.setOnClickListener {
            val intent = Intent(this,MenuAdministrador::class.java)
            startActivity(intent)
            finish()
        }
    }
}