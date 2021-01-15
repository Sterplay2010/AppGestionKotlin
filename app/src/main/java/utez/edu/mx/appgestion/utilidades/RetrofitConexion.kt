package utez.edu.mx.appgestion.utilidades

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitConexion {
    companion object{
        fun getRetrofit():Retrofit{
            var retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("http://192.168.0.3:8080/RESTrotoplas_war/servicio/")
                    .build()
            return retrofit
        }
    }
}