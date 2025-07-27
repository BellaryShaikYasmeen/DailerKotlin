import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.dailerkotlin.Call
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.launch
@Database(entities = [Contact::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao
    abstract fun callDao(): CallDao
    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

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

                // Insert dummy contacts and calls
                CoroutineScope(Dispatchers.IO).launch {
                    val contactDao = instance.contactDao()
                    val callDao = instance.callDao()

                    if (contactDao.getAllContacts().isEmpty()) {
                        val dummyContacts = listOf(
                            Contact(name = "Alice Smith", phoneNumber = "123-456-7890"),
                            Contact(name = "Bob Johnson", phoneNumber = "555-555-5555"),
                            Contact(name = "Carol Williams", phoneNumber = "987-654-3210"),
                            Contact(name = "David Brown", phoneNumber = "111-222-3333")
                        )
                        dummyContacts.forEach { contactDao.insert(it) }
                        Log.d("AppDatabase", "Inserted dummy contacts.")
                    }

                    if (callDao.getAllCalls().count()==0) {
                        val dummyCalls = listOf(
                            Call(contactName = "Alice Smith", phoneNumber = "123-456-7890", callType = "Incoming", timestamp = System.currentTimeMillis()),
                            Call(contactName = "Bob Johnson", phoneNumber = "555-555-5555", callType = "Outgoing", timestamp = System.currentTimeMillis() - 50000),
                            Call(contactName = "Carol Williams", phoneNumber = "987-654-3210", callType = "Missed", timestamp = System.currentTimeMillis() - 100000)
                        )
                        dummyCalls.forEach { callDao.insert(it) }
                        Log.d("AppDatabase", "Inserted dummy calls.")
                    }
                }

                instance
            }
        }
    }
}
