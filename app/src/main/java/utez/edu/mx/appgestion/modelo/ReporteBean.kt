package utez.edu.mx.appgestion.modelo

import com.google.gson.annotations.SerializedName

class ReporteBean(
        @SerializedName("idReporte") var idReporte: Int,
        @SerializedName("fechaHora") var fechaHora: String,
        @SerializedName("descripcion") var descripcion: String,
        @SerializedName("idEmpleado") var idEmpleado: Int,
        @SerializedName("estatus") var estatus: Int,
        var comentarioFinal: String,
        var fechaResolucion:String
)