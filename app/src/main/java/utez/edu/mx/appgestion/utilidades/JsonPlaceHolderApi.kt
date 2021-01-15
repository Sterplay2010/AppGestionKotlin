package utez.edu.mx.appgestion.utilidades

import retrofit2.Call
import retrofit2.http.*
import utez.edu.mx.appgestion.modelo.*

interface JsonPlaceHolderApi {

    //Metodos de Inicio de Sesión
    @GET("empleado/{email}")
    fun iniciarSesion(@Path("email")email:String):Call<EmpleadoBean>

    //Metodos de Administrador
    @POST("insertarEmpleado")
    fun insertarEmpleado(@Body empleadoPost:EmpleadoPost):Call<Boolean>

    @GET("consultarEmpleados")
    fun consultarEmpleados():Call<ArrayList<EmpleadoBean>>

    @DELETE("eliminarEmpleado/{idEmp}")
    fun eliminarEmpleado(@Path("idEmp")idEmp: Int):Call<Boolean>

    //Metodo de modificar perfil
    @PUT("actualizarEmpleado")
    fun actualizarEmpleado(@Body empleadoPost: EmpleadoPost):Call<Boolean>

    //Metodos de Empleado
    @POST("insertarReporte")
    fun insertarReporte(@Body reportePost: ReportePost):Call<Boolean>

    @GET("consultarReportesEmpleados/{idEmp}")
    fun consultarReporteId(@Path("idEmp")idEmp:Int):Call<ArrayList<ReporteBean>>

    @GET("consultarComentario/{idReporte}")
    fun consultarComentario(@Path("idReporte")idReporte:Int):Call<ResolucionBean>

    //Metodos de Personal de servicios
    @GET("consultarReportes")
    fun consultarReportes():Call<ArrayList<ReporteBeanServicios>>

    @GET("consultarEmpId/{idEmp}")
    fun consultarEmpId(@Path("idEmp")idEmp: Int):Call<EmpleadoBean>

    @POST("resolucion")
    fun resolver(@Query("idReporte")idReporte: Int,@Query("comentario")comentario:String):Call<Boolean>

}