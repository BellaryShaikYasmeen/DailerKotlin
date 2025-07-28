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
                    if (contactsCount == 0) {
                        val dummyContacts = listOf(
                            Contact(name = "Alice Smith", phoneNumber = "123-456-7890"),
                            Contact(name = "Bob Johnson", phoneNumber = "555-555-5555"),
                            Contact(name = "Carol Williams", phoneNumber = "987-654-3210"),
                            Contact(name = "David Brown", phoneNumber = "111-222-3333")
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
