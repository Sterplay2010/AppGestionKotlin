package utez.edu.mx.appgestion.adaptadores

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import utez.edu.mx.appgestion.R
import utez.edu.mx.appgestion.modelo.ReporteBean
import utez.edu.mx.appgestion.modelo.ReporteBeanServicios
import utez.edu.mx.appgestion.modelo.ResolucionBean
import utez.edu.mx.appgestion.vistas.AgregarComentario

class AdaptadorResolver(var lista:ArrayList<ReporteBeanServicios>):RecyclerView.Adapter<AdaptadorResolver.ViewHolder>(){
    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        fun binItems(data:ReporteBeanServicios){
            val area:TextView=itemView.findViewById(R.id.txtItemArea)
            val fechaHora:TextView=itemView.findViewById(R.id.txtItemFechaHRegistro)
            val nombreEmp:TextView=itemView.findViewById(R.id.txtItemEmpleado)
            val descripcion:TextView=itemView.findViewById(R.id.txtItemDescripcion)
            val estatus:TextView=itemView.findViewById(R.id.txtItemStatus)
            area.text = data.areaEmpleado
            fechaHora.text = data.fechaHora
            nombreEmp.text = data.nombreEmpleado
            descripcion.text = data.descripcion
            if (data.estatus==1){
                estatus.text = "Resuelto"
            }else if (data.estatus==0){
                estatus.text = "No resuelto"
            }
            itemView.findViewById<Button>(R.id.btnResolverItem).setOnClickListener {
             if (data.estatus==0){
                 val intent = Intent(itemView.context,AgregarComentario::class.java)
                 var idReporte = data.idReporte
                 var b : Bundle = Bundle()
                 b.putInt("idReporte",idReporte)
                 intent.putExtras(b)
                 itemView.context.startActivity(intent)
                 (itemView.context as Activity).finish()
             }else if (data.estatus==1){
                 Toast.makeText(itemView.context,"El reporte ya se resolvio",Toast.LENGTH_LONG).show()
             }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_reporte,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.binItems(lista[position])
    }
}