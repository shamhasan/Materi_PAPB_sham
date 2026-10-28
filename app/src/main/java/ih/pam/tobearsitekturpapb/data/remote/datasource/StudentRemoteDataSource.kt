package ih.pam.tobearsitekturpapb.data.remote.datasource

import ih.pam.tobearsitekturpapb.data.remote.api.StudentApiService
import ih.pam.tobearsitekturpapb.data.remote.dto.UserDto
import ih.pam.tobearsitekturpapb.data.remote.dto.UserResponse
import okhttp3.Callback
import retrofit2.Call
import retrofit2.Response

class StudentRemoteDataSource(private val api: StudentApiService) {
    suspend fun getStudents(): List<UserDto> =
        api.getUsers(limit = 30).users

    suspend fun getStudent(id: Int): UserDto =
        api.getUser(
            id = id
        )

    suspend fun searchStudents(query: String): List<UserDto> =
        api.searchUser(query).users
}