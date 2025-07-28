import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

// Add your entities here
@Database(entities = [Contact::class], version = 1, exportSchema = false)
// Add @TypeConverters if needed, e.g. @TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "contacts_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance

                // Insert dummy contacts asynchronously
                CoroutineScope(Dispatchers.IO).launch {
                    val contactDao = instance.contactDao()

                    // Get current contact count via Flow
                    val contactsCount = contactDao.getAllContactsFlow().first().size
                    if (contactsCount != 0) {
                        val dummyContacts = listOf(
                        Contact(name = "Fiona Garcia", phoneNumber = "333-444-5555"),
                        Contact(name = "George Harris", phoneNumber = "444-555-6666"),
                        Contact(name = "Hannah Lewis", phoneNumber = "555-666-7777"),
                        Contact(name = "Ian Clark", phoneNumber = "666-777-8888"),
                        Contact(name = "Julia Hall", phoneNumber = "777-888-9999"),
                        Contact(name = "Kevin Young", phoneNumber = "888-999-0000"),
                        Contact(name = "Lily Allen", phoneNumber = "999-000-1111"),
                        Contact(name = "Michael Scott", phoneNumber = "000-111-2222"),
                        Contact(name = "Nina Adams", phoneNumber = "111-222-3333"),
                        Contact(name = "Oscar Perez", phoneNumber = "222-333-4444"),
                        Contact(name = "Paula Turner", phoneNumber = "333-444-5555"),
                        Contact(name = "Quentin Reed", phoneNumber = "444-555-6666"),
                        Contact(name = "Rachel King", phoneNumber = "555-666-7777"),
                        Contact(name = "Steve Moore", phoneNumber = "666-777-8888"),
                        Contact(name = "Tina Baker", phoneNumber = "777-888-9999"),
                        Contact(name = "Uma Nelson", phoneNumber = "888-999-0000"),
                        Contact(name = "Victor Cox", phoneNumber = "999-000-1111"),
                        Contact(name = "Wendy Rogers", phoneNumber = "000-111-2222"),
                        Contact(name = "Xavier Jenkins", phoneNumber = "111-222-3333"),
                        Contact(name = "Yara Simmons", phoneNumber = "222-333-4444"),
                        Contact(name = "Zachary Bell", phoneNumber = "333-444-5555")


                        )
                        dummyContacts.forEach { contactDao.insert(it) }
                        Log.d("AppDatabase", "Inserted dummy contacts.")
                    }
                }

                instance
            }
        }
    }
}
