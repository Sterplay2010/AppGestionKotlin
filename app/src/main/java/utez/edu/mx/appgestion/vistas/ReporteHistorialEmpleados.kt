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
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import utez.edu.mx.appgestion.R
import utez.edu.mx.appgestion.adaptadores.AdaptadorReporte
import utez.edu.mx.appgestion.databinding.ActivityReporteHistorialEmpleadosBinding
import utez.edu.mx.appgestion.modelo.ReporteBean
import utez.edu.mx.appgestion.modelo.ResolucionBean
import utez.edu.mx.appgestion.utilidades.JsonPlaceHolderApi
import utez.edu.mx.appgestion.utilidades.RetrofitConexion
import java.util.ArrayList

class ReporteHistorialEmpleados : AppCompatActivity() {
    lateinit var preferences: SharedPreferences
    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reporte_historial_empleados)
        //Shared Preferences
        preferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val idEmp = preferences.getInt("IDEMPLEADO", 0)
        Log.d("idEmp",idEmp.toString())
        //View Binding
        val binding = ActivityReporteHistorialEmpleadosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Creación del RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.reportesEmp)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        //Llamada a retrofit
        val jsonPlaceHolderApi = RetrofitConexion.getRetrofit().create(JsonPlaceHolderApi::class.java)
        val call: Call<ArrayList<ReporteBean>> = jsonPlaceHolderApi.consultarReporteId(idEmp)
        call.enqueue(object : Callback<ArrayList<ReporteBean>> {
            override fun onFailure(call: Call<ArrayList<ReporteBean>>, t: Throwable) {
                Log.e("ERROR", t.message.toString())
            }

            override fun onResponse(call: Call<ArrayList<ReporteBean>>, response: Response<ArrayList<ReporteBean>>) {
                val Reportes:ArrayList<ReporteBean> = response.body()!!
                val listaReportes = ArrayList<ReporteBean>()
                var call2:Call<ResolucionBean>
                for (reporte in Reportes){
                    var idReporte = reporte.idReporte
                    var fechaHora = reporte.fechaHora
                    var descripcion = reporte.descripcion
                    call2 = jsonPlaceHolderApi.consultarComentario(idReporte)
                    call2.enqueue(object :Callback<ResolucionBean>{
                        override fun onFailure(call: Call<ResolucionBean>, t: Throwable) {
                            Log.e("ERROR",t.message.toString())
                        }

                        override fun onResponse(call: Call<ResolucionBean>, response: Response<ResolucionBean>) {
                            var comentarioFinal = response.body()?.comentario
                            var fechaFinal = response.body()?.fechaHoraResolucion
                            if (comentarioFinal != null) {
                                Log.d("ComentarioFinal",comentarioFinal)
                            }
                            if (fechaFinal != null) {
                                Log.d("fechaFinal",fechaFinal)
                            }
                            if (comentarioFinal!=null&&fechaFinal!=null){
                                listaReportes.add(ReporteBean(idReporte,fechaHora,descripcion,0,0,comentarioFinal,fechaFinal))
                                llamar(listaReportes,recyclerView)
                            }else if (comentarioFinal==null){
                                listaReportes.add(ReporteBean(idReporte,fechaHora,descripcion,0,0,"",""))
                                llamar(listaReportes,recyclerView)
                            }

                        }

                    })
                }

            }
        })

        binding.btnRegresarHistorial.setOnClickListener {
            val intent = Intent(this,MenuEmpleados::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun llamar(listaReporte:ArrayList<ReporteBean>,recyclerView:RecyclerView) {
        val adapter = AdaptadorReporte(listaReporte)
        recyclerView.adapter = adapter
    }
}