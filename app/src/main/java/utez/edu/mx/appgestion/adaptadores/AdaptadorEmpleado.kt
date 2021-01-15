package utez.edu.mx.appgestion.adaptadores

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.telecom.Call
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Callback
import retrofit2.Response
import utez.edu.mx.appgestion.R
import utez.edu.mx.appgestion.modelo.EmpleadoBean
import utez.edu.mx.appgestion.utilidades.JsonPlaceHolderApi
import utez.edu.mx.appgestion.utilidades.RetrofitConexion
import utez.edu.mx.appgestion.vistas.ConsultarEmpleado

class AdaptadorEmpleado (var lista:ArrayList<EmpleadoBean>):RecyclerView.Adapter<AdaptadorEmpleado.ViewHolder>(){
    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        fun binItems(data:EmpleadoBean){
            val nombreCompleto:TextView=itemView.findViewById(R.id.textNombreEmpleadoItem)
            val telefono:TextView=itemView.findViewById(R.id.textTelefonoEmpleadoItem)
            val email:TextView=itemView.findViewById(R.id.textMailEmpleadoItem)
            val area:TextView=itemView.findViewById(R.id.textAreaEmpleadoItem)
            val puesto:TextView=itemView.findViewById(R.id.textPuestoEmpleadoItem)
            nombreCompleto.text = data.nombreCompleto
            telefono.text = data.telefono
            email.text = data.email
            area.text = data.area
            if (data.idRol==2){
                puesto.text = "Empleado"
            }else if(data.idRol==3){
                puesto.text = "Personal De Servicios"
            }
            itemView.findViewById<Button>(R.id.eliminarEmpleado).setOnClickListener {
                val jsonPlaceHolderApi = RetrofitConexion.getRetrofit().create(JsonPlaceHolderApi::class.java)
                val call:retrofit2.Call<Boolean> = jsonPlaceHolderApi.eliminarEmpleado(data.idEmp)
                call.enqueue(object :Callback<Boolean>{
                    override fun onFailure(call: retrofit2.Call<Boolean>, t: Throwable) {
                       Log.e("ERROR",t.message.toString())
                    }

                    override fun onResponse(call: retrofit2.Call<Boolean>,response: Response<Boolean>) {
                        Toast.makeText(itemView.context.applicationContext,"Eliminado con exito",Toast.LENGTH_LONG).show()
                        val intent = Intent(itemView.context,ConsultarEmpleado::class.java)
                        itemView.context.startActivity(intent)
                        (itemView.context as Activity).finish()
                    }
                })
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_empleado,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binItems(lista[position])
    }
}