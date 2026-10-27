package ih.pam.tobearsitekturpapb.data.repository

import ih.pam.tobearsitekturpapb.data.datasource.StudentLocalDataSource
import ih.pam.tobearsitekturpapb.data.model.Student

interface StudentRepository {
    fun getStudentById(id: Int): Student?
    fun getStudents(): List<Student>
}

class DefaultStudentRepository(
    private val dataSource: StudentLocalDataSource
) : StudentRepository {
        override fun getStudentById(id: Int): Student? {
        return dataSource.getStudentById(id)
    }

    override fun getStudents(): List<Student> {
        return dataSource.getAllStudents()
    }


}