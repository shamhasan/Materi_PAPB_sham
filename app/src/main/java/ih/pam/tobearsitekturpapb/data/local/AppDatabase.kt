package ih.pam.tobearsitekturpapb.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlin.concurrent.Volatile

@Database(
    entities = [FavoriteStudentEntity::class],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase: RoomDatabase() {
    abstract fun favoriteStudentDao(): FavoriteStudentDao
}

object DatabaseProvider{
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase =
        INSTANCE ?: synchronized(this){
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "navigation_lab.db"
            ).build().also { INSTANCE = it }
        }
}