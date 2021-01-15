package utez.edu.mx.appgestion.vistas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import utez.edu.mx.appgestion.R
import utez.edu.mx.appgestion.databinding.ActivityAgregarComentarioBinding
import utez.edu.mx.appgestion.utilidades.JsonPlaceHolderApi
import utez.edu.mx.appgestion.utilidades.RetrofitConexion
import java.text.SimpleDateFormat
import java.util.*

class AgregarComentario : AppCompatActivity() {
    var seHizo:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_comentario)
        //View Binding
        val binding = ActivityAgregarComentarioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Recuperar id del reporte
        var bundle = intent.extras
        var idReporte = bundle?.getInt("idReporte")
        //Hora del sistema
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val currentDateAndTime: String = simpleDateFormat.format(Date())
        binding.txtFechaHoraAtencion.text = currentDateAndTime
        Log.d("idReporte",idReporte.toString())
        binding.btnGuardarComentario.setOnClickListener {
           if (seHizo==1){
               Toast.makeText(applicationContext,"Este reporte ya se soluciono",Toast.LENGTH_LONG).show()
           }else{
               var comentario = binding.txtComentarioFinal.text.toString()
               //Objeto de Retrofit
               val jsonPlaceHolderApi = RetrofitConexion.getRetrofit().create(JsonPlaceHolderApi::class.java)
               if (idReporte!=null){
                   val call:Call<Boolean> = jsonPlaceHolderApi.resolver(idReporte,comentario)
                   call.enqueue(object :Callback<Boolean>{
                       override fun onFailure(call: Call<Boolean>, t: Throwable) {
                           Log.e("ERROR",t.message.toString())
                       }
                       override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                           validar(binding)
                           Toast.makeText(applicationContext,"Reporte atendido con exito",Toast.LENGTH_LONG).show()
                       }
                   })
               }
           }
        }

        binding.btnRegresarComentario.setOnClickListener {
            val intent = Intent(this,ResolverPeticion::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun validar(binding:ActivityAgregarComentarioBinding){
        seHizo = 1
        binding.txtComentarioFinal.setText("")
    }
}