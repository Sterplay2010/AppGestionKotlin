package utez.edu.mx.appgestion.adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import utez.edu.mx.appgestion.R
import utez.edu.mx.appgestion.modelo.ReporteBean

class AdaptadorReporte (var lista:ArrayList<ReporteBean>):RecyclerView.Adapter<AdaptadorReporte.ViewHolder>(){
    class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        fun binItems(data: ReporteBean){
            val fechaReporte:TextView=itemView.findViewById(R.id.fechaReporte)
            val descripcion:TextView=itemView.findViewById(R.id.descripcion)
            val comentario:TextView=itemView.findViewById(R.id.comentario)
            val fechaResolucion:TextView=itemView.findViewById(R.id.resolucion)
            fechaReporte.text = data.fechaHora
            descripcion.text = data.descripcion
            if (data.comentarioFinal.equals("")){
                comentario.text = "No resuelto"
            }else{
                comentario.text = data.comentarioFinal
            }
            if (data.fechaResolucion.equals("")){
                fechaResolucion.text = "No resuelto"
            }else{
                fechaResolucion.text = data.fechaResolucion
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.content_item,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binItems(lista[position])
    }
}