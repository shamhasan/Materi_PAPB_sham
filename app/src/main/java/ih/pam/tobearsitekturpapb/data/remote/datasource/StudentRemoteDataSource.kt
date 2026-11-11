package ih.pam.tobearsitekturpapb.data.remote.datasource

import ih.pam.tobearsitekturpapb.data.remote.api.StudentApiService
import ih.pam.tobearsitekturpapb.data.remote.dto.UserDto
import ih.pam.tobearsitekturpapb.data.remote.dto.UserResponse
import okhttp3.Callback
import retrofit2.Call
import retrofit2.Response

class StudentRemoteDataSource(private val api: StudentApiService) {
    suspend fun getStudents(): List<UserDto> {
        return api.getUsers(limit = 10).users
    }

    suspend fun getStudent(id: Int): UserDto {
        return api.getUser(id = id)
    }

    suspend fun searchStudents(query: String): List<UserDto> {
        return api.searchUser(query = query).users
    }
}