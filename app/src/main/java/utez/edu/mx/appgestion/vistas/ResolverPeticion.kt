package utez.edu.mx.appgestion.vistas

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create
import utez.edu.mx.appgestion.R
import utez.edu.mx.appgestion.adaptadores.AdaptadorResolver
import utez.edu.mx.appgestion.databinding.ActivityMenuPersonalBinding
import utez.edu.mx.appgestion.databinding.ActivityResolverPeticionBinding
import utez.edu.mx.appgestion.modelo.EmpleadoBean
import utez.edu.mx.appgestion.modelo.ReporteBeanServicios
import utez.edu.mx.appgestion.utilidades.JsonPlaceHolderApi
import utez.edu.mx.appgestion.utilidades.RetrofitConexion


class ResolverPeticion : AppCompatActivity() {
    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resolver_peticion)

        //View Binding
        val binding = ActivityResolverPeticionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Creación del RyclerView
        val recyclerView: RecyclerView = findViewById(R.id.finalRes)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        //Objeto de Retrofit
        val jsonPlaceHolderApi = RetrofitConexion.getRetrofit().create(JsonPlaceHolderApi::class.java)
        val call: Call<ArrayList<ReporteBeanServicios>> = jsonPlaceHolderApi.consultarReportes()
        call.enqueue(object : Callback<ArrayList<ReporteBeanServicios>> {
            override fun onFailure(call: Call<ArrayList<ReporteBeanServicios>>, t: Throwable) {
                Log.e("ERROR", t.message.toString())
            }

            override fun onResponse(call: Call<ArrayList<ReporteBeanServicios>>, response: Response<ArrayList<ReporteBeanServicios>>) {
                val Reportes: ArrayList<ReporteBeanServicios> = response.body()!!
                val listaReporte = ArrayList<ReporteBeanServicios>()
                var call2: Call<EmpleadoBean>
                for (reporte in Reportes) {
                    var fechaHora = reporte.fechaHora
                    var descripcion = reporte.descripcion
                    var estatus = reporte.estatus
                    var idEmp = reporte.idEmpleado
                    var idReporte = reporte.idReporte
                    call2 = jsonPlaceHolderApi.consultarEmpId(idEmp)
                    call2.enqueue(object : Callback<EmpleadoBean> {
                        override fun onFailure(call: Call<EmpleadoBean>, t: Throwable) {
                            Log.e("ERROR", t.message.toString())
                        }

                        override fun onResponse(call: Call<EmpleadoBean>, response: Response<EmpleadoBean>) {
                            var nombreEmpleado = response.body()?.nombreCompleto
                            var areaEmpleado = response.body()?.area
                            if (nombreEmpleado != null) {
                                Log.d("nombre",nombreEmpleado)
                            }
                            if (areaEmpleado != null) {
                                Log.d("area",areaEmpleado)
                            }
                            if (nombreEmpleado!=null&&areaEmpleado!=null){
                                listaReporte.add(ReporteBeanServicios(idReporte,fechaHora,descripcion,idEmp,estatus,nombreEmpleado,areaEmpleado))
                                llenarView(listaReporte,recyclerView)
                            }
                        }
                    })
                }
            }
        })

        binding.btnRegresar.setOnClickListener {
            val intent = Intent(this,MenuPersonal::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun llenarView(listaReportes:ArrayList<ReporteBeanServicios>,recyclerView:RecyclerView) {
        val adapter = AdaptadorResolver(listaReportes)
        recyclerView.adapter = adapter
    }
}