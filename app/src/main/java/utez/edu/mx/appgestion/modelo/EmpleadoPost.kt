package utez.edu.mx.appgestion.modelo

import com.google.gson.annotations.SerializedName

class EmpleadoPost(
        var id: Int,
        var nombreCompleto: String,
        var domicilio: String,
        var sexo: String,
        var fechaNacimiento: String,
        var email: String,
        var telefono: String,
        var contrasenia: String,
        var area: String,
        var idRol: Int

)