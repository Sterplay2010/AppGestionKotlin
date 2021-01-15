package utez.edu.mx.appgestion.modelo

import com.google.gson.annotations.SerializedName

class EmpleadoBean (
        @SerializedName("id") var idEmp:Int,
        @SerializedName("nombreCompleto") var nombreCompleto:String,
        @SerializedName("domicilio") var domicilio:String,
        @SerializedName("sexo") var sexo:String,
        @SerializedName("fechaNacimiento") var fechaNacimiento:String,
        @SerializedName("email") var email:String,
        @SerializedName("telefono") var telefono:String,
        @SerializedName("contrasenia") var contrasenia:String,
        @SerializedName("area") var area:String,
        @SerializedName("idRol") var idRol:Int

)