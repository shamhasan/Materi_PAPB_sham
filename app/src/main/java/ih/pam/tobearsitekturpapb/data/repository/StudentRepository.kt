package ih.pam.tobearsitekturpapb.data.repository

import ih.pam.tobearsitekturpapb.data.datasource.StudentLocalDataSource
import ih.pam.tobearsitekturpapb.data.model.Student
import ih.pam.tobearsitekturpapb.data.remote.datasource.StudentRemoteDataSource
import ih.pam.tobearsitekturpapb.data.remote.dto.toStudent

interface StudentRepository {
    suspend fun getStudentById(id: Int): Student
    suspend fun getStudents(): List<Student>
    suspend fun searchStudents(query: String): List<Student>
}

class DefaultStudentRepository(
    private val remoteDataSource: StudentRemoteDataSource,
) : StudentRepository {
    override suspend fun getStudentById(id: Int): Student {
        return remoteDataSource.getStudent(id).toStudent()
    }

    override suspend fun getStudents(): List<Student> {
        return remoteDataSource.getStudents().map { it.toStudent() }
    }

    override suspend fun searchStudents(query: String): List<Student> {
        return remoteDataSource.searchStudents(query).map { it.toStudent() }
    }
}