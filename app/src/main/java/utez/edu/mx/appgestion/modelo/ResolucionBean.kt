package utez.edu.mx.appgestion.modelo

import com.google.gson.annotations.SerializedName

class ResolucionBean(
        @SerializedName("idResolucion") var idResolucion:Int,
        @SerializedName("idReporte") var idReporte:Int,
        @SerializedName("fechaHoraResolucion") var fechaHoraResolucion:String,
        @SerializedName("comentario") var comentario:String
)