package ih.pam.tobearsitekturpapb.data.repository

import ih.pam.tobearsitekturpapb.data.local.FavoriteStudentDao
import ih.pam.tobearsitekturpapb.data.local.FavoriteStudentEntity
import ih.pam.tobearsitekturpapb.data.model.Student
import kotlinx.coroutines.flow.Flow

class FavoriteRepository(
    private val dao: FavoriteStudentDao
) {
    fun observeIsFavorite(id: Int) : Flow<Boolean> =
        dao.observeIsFavorite(id)

    fun observeFavorites(): Flow<List<FavoriteStudentEntity>> =
        dao.observeFavorites()

    suspend fun add(student: Student){
        dao.insert(
            FavoriteStudentEntity(
                id = student.id,
                firstName = student.name,
                lastName = "",
                email = student.email
            )
        )
    }

   suspend fun remove(id: Int){
       dao.deleteById(id)
   }
}