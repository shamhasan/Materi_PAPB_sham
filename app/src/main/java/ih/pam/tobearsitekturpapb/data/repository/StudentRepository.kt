package ih.pam.tobearsitekturpapb.data.repository

import ih.pam.tobearsitekturpapb.data.datasource.StudentLocalDataSource
import ih.pam.tobearsitekturpapb.data.model.Student
import ih.pam.tobearsitekturpapb.data.remote.datasource.StudentRemoteDataSource
import ih.pam.tobearsitekturpapb.data.remote.dto.toStudent

interface StudentRepository {
    fun getStudentById(id: Int, callback: (Result<Student>) -> Unit)
    fun getStudents(callback: (Result<List<Student>>) -> Unit)
}

class DefaultStudentRepository(
    private val remoteDataSource: StudentRemoteDataSource,
) : StudentRepository {

    override fun getStudentById(
        id: Int,
        callback: (Result<Student>) -> Unit,
    ) {
        remoteDataSource.getStudent(id) { result ->
            callback(result.map { it.toStudent() })
        }
    }

    override fun getStudents(callback: (Result<List<Student>>) -> Unit) {
        remoteDataSource.getStudents { result ->
            callback(result.map { users -> users.map { it.toStudent() } })
        }
    }


}