package utez.edu.mx.appgestion.modelo

import com.google.gson.annotations.SerializedName

class ReportePost(
        var idReporte: Int,
        var fechaHora: String,
        var descripcion: String,
        var idEmpleado: Int,
        var estatus: Int
)