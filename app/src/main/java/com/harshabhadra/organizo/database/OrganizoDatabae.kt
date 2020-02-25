package com.harshabhadra.organizo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.harshabhadra.organizo.ioThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Database(entities = [Task::class, Category::class], version = 5, exportSchema = false)
abstract class OrganizoDatabae : RoomDatabase() {

    //Connect the database to dao
    abstract val TaskDao: TaskDao
    abstract val categoryDao:CategoryDao

    /**
     * Define a companion object, this allows us to add functions on the SleepDatabase class.
     *
     * For example, clients can call `SleepDatabase.getInstance(context)` to instantiate
     * a new SleepDatabase.
     */
    companion object {

        /**
         * INSTANCE will keep a reference to any database returned via getInstance.
         *
         * This will help us avoid repeatedly initializing the database, which is expensive.
         *
         *  The value of a volatile variable will never be cached, and all writes and
         *  reads will be done to and from the main memory. It means that changes made by one
         *  thread to shared data are visible to other threads.
         */
        @Volatile
        private var INSTANCE: OrganizoDatabae? = null

        val PREPOPULATE_DATA = listOf(Category("Personal"),
            Category("Work"), Category
        ("Health")
        )

        fun getInstance(context: Context): OrganizoDatabae {

            // Multiple threads can ask for the database at the same time, ensure we only initialize
            // it once by using synchronized. Only one thread may enter a synchronized block at a
            // time.
            synchronized(this) {

                var instance = INSTANCE
                if (instance == null) {

                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        OrganizoDatabae::class.java,
                        "Organizo_databse"
                    ).addCallback(object : Callback(){
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // insert the data on the IO Thread
                            ioThread {
                                getInstance(context).categoryDao.insertListOfCategory(PREPOPULATE_DATA)
                            }
                        }
                    })
                        .fallbackToDestructiveMigration()
                        .build()
                }
                return instance
            }
        }
    }
}