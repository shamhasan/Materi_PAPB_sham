package ih.pam.tobearsitekturpapb.data.remote

import ih.pam.tobearsitekturpapb.data.remote.api.StudentApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://dummyjson.com/"

    val apiService: StudentApiService by lazy {
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build().create(
            StudentApiService::class.java)
    }
}