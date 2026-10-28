package ih.pam.tobearsitekturpapb.data.remote.datasource

import ih.pam.tobearsitekturpapb.data.remote.api.StudentApiService
import ih.pam.tobearsitekturpapb.data.remote.dto.UserDto
import ih.pam.tobearsitekturpapb.data.remote.dto.UserResponse
import okhttp3.Callback
import retrofit2.Call
import retrofit2.Response

class StudentRemoteDataSource(private val api: StudentApiService) {
    fun getStudents(callback: (Result<List<UserDto>>) -> Unit) {
        api.getUsers().enqueue(object : retrofit2.Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse?>, response: Response<UserResponse?>) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    callback(Result.success(body.users))
                } else {
                    callback(Result.failure(Exception(response.message())))
                }
            }

            override fun onFailure(call: Call<UserResponse?>, t: Throwable) {
                callback(Result.failure(t))
            }
        })
    }

    fun getStudent(id: Int, callback: (Result<UserDto>) -> Unit){
        api.getUser(id).enqueue(object : retrofit2.Callback<UserDto> {
            override fun onResponse(call: Call<UserDto?>, response: Response<UserDto?>) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    callback(Result.success(body))
                } else {
                    callback(Result.failure(Exception("HTTP ${response.code()}")))
                }
            }

            override fun onFailure(call: Call<UserDto?>, t: Throwable) {
               callback(Result.failure(t))
            }
        })

    }
}