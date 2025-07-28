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
@Database(entities = [Contact::class], version = 3, exportSchema = false)
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
contactDao.deleteAll();
                    // Get current contact count via Flow
                    val contactsCount = contactDao.getAllContactsFlow().first().size
                    if (contactsCount == 0) {
                        val dummyContacts = listOf(
                            // Hindi Industry
                            Contact(name = "Shah Rukh Khan", phoneNumber = "98765-43210", photoPath = null),
                            Contact(name = "Amitabh Bachchan", phoneNumber = "94444-55555", photoPath = null),
                            Contact(name = "Priyanka Chopra", phoneNumber = "93332-12345", photoPath = null),
                            Contact(name = "Virat Kohli", phoneNumber = "90000-23456", photoPath = null),
                            Contact(name = "Deepika Padukone", phoneNumber = "98888-77777", photoPath = null),
                            Contact(name = "Ranbir Kapoor", phoneNumber = "93456-78901", photoPath = null),
                            Contact(name = "Alia Bhatt", phoneNumber = "99123-45678", photoPath = null),
                            Contact(name = "Rajinikanth", phoneNumber = "97765-43210", photoPath = null),
                            Contact(name = "Kareena Kapoor", phoneNumber = "98234-56789", photoPath = null),
                            Contact(name = "Salman Khan", phoneNumber = "96000-22222", photoPath = null),
                            Contact(name = "Akshay Kumar", phoneNumber = "93777-88888", photoPath = null),
                            Contact(name = "Katrina Kaif", phoneNumber = "98765-54321", photoPath = null),
                            Contact(name = "Ranveer Singh", phoneNumber = "96123-98765", photoPath = null),
                            Contact(name = "Hrithik Roshan", phoneNumber = "99887-65432", photoPath = null),
                            Contact(name = "Anushka Sharma", phoneNumber = "92222-33333", photoPath = null),
                            Contact(name = "Sridevi Kapoor", phoneNumber = "94444-98765", photoPath = null),
                            Contact(name = "Madhuri Dixit", phoneNumber = "93456-12345", photoPath = null),
                            Contact(name = "Rani Mukerji", phoneNumber = "98888-55555", photoPath = null),
                            Contact(name = "Jacqueline Fernandez", phoneNumber = "99123-78901", photoPath = null),
                            Contact(name = "Deepika Padukone", phoneNumber = "97987-34567", photoPath = null),

                            // Kannada Industry
                            Contact(name = "Yash", phoneNumber = "90999-98765", photoPath = null),
                            Contact(name = "Sudeep", phoneNumber = "93456-67890", photoPath = null),
                            Contact(name = "Radhika Pandit", phoneNumber = "98877-55555", photoPath = null),
                            Contact(name = "Darshan", phoneNumber = "92234-67890", photoPath = null),
                            Contact(name = "Puneeth Rajkumar", phoneNumber = "96123-45678", photoPath = null),
                            Contact(name = "Ramesh Aravind", phoneNumber = "97234-56789", photoPath = null),
                            Contact(name = "Nivedha Thomas", phoneNumber = "99234-87654", photoPath = null),
                            Contact(name = "Kiccha Sudeep", phoneNumber = "90987-65432", photoPath = null),
                            Contact(name = "Shruti Haasan", phoneNumber = "98877-23456", photoPath = null),
                            Contact(name = "Prakash Raj", phoneNumber = "97765-43211", photoPath = null),

                            // Tamil Industry
                            Contact(name = "Rajinikanth", phoneNumber = "94444-55555", photoPath = null),
                            Contact(name = "Vijay", phoneNumber = "92234-67890", photoPath = null),
                            Contact(name = "Ajith Kumar", phoneNumber = "97987-65432", photoPath = null),
                            Contact(name = "Kamal Haasan", phoneNumber = "99012-34567", photoPath = null),
                            Contact(name = "Nayanthara", phoneNumber = "93456-98765", photoPath = null),
                            Contact(name = "Vijay Sethupathi", phoneNumber = "96234-56789", photoPath = null),
                            Contact(name = "Dhanush", phoneNumber = "97765-43210", photoPath = null),
                            Contact(name = "Samantha Akkineni", phoneNumber = "93677-12345", photoPath = null),
                            Contact(name = "Trisha Krishnan", phoneNumber = "98988-76543", photoPath = null),
                            Contact(name = "Radhika Apte", phoneNumber = "92233-88888", photoPath = null),

                            // Telugu Industry
                            Contact(name = "Chiranjeevi", phoneNumber = "90999-12345", photoPath = null),
                            Contact(name = "Mahesh Babu", phoneNumber = "92234-56789", photoPath = null),
                            Contact(name = "Allu Arjun", phoneNumber = "96000-23456", photoPath = null),
                            Contact(name = "Pawan Kalyan", phoneNumber = "93456-78901", photoPath = null),
                            Contact(name = "NTR Jr", phoneNumber = "92234-67890", photoPath = null),
                            Contact(name = "Prabhas", phoneNumber = "98877-65432", photoPath = null),
                            Contact(name = "Ravi Teja", phoneNumber = "92234-87654", photoPath = null),
                            Contact(name = "Anushka Shetty", phoneNumber = "99123-67890", photoPath = null),
                            Contact(name = "Kajal Aggarwal", phoneNumber = "93987-23456", photoPath = null),
                            Contact(name = "Tamannaah Bhatia", phoneNumber = "98654-98765", photoPath = null),

                            // Malayalam Industry
                            Contact(name = "Mohanlal", phoneNumber = "90999-54321", photoPath = null),
                            Contact(name = "Mammootty", phoneNumber = "94444-23456", photoPath = null),
                            Contact(name = "Prithviraj Sukumaran", phoneNumber = "97665-54321", photoPath = null),
                            Contact(name = "Nivin Pauly", phoneNumber = "93234-98765", photoPath = null),
                            Contact(name = "Fahadh Faasil", phoneNumber = "91234-87654", photoPath = null),
                            Contact(name = "Dulquer Salmaan", phoneNumber = "98765-43210", photoPath = null),
                            Contact(name = "Parvathy Thiruvothu", phoneNumber = "99234-87654", photoPath = null),
                            Contact(name = "Nazriya Nazim", phoneNumber = "99012-34567", photoPath = null),
                            Contact(name = "Manju Warrier", phoneNumber = "97987-65432", photoPath = null),
                            Contact(name = "Indrajith Sukumaran", phoneNumber = "97987-65432", photoPath = null),

                            // Additional Celebrities from multiple industries
                            Contact(name = "Shruti Haasan", phoneNumber = "97000-11111", photoPath = null),
                            Contact(name = "Rakul Preet Singh", phoneNumber = "96000-12345", photoPath = null),
                            Contact(name = "Kriti Sanon", phoneNumber = "98777-76543", photoPath = null),
                            Contact(name = "Bipasha Basu", phoneNumber = "93332-76543", photoPath = null),
                            Contact(name = "Kareena Kapoor", phoneNumber = "98234-98765", photoPath = null),
                            Contact(name = "Sonam Kapoor", phoneNumber = "91999-87654", photoPath = null),
                            Contact(name = "Vidya Balan", phoneNumber = "92233-78901", photoPath = null),
                            Contact(name = "Bhumi Pednekar", phoneNumber = "93322-11111", photoPath = null),
                            Contact(name = "Sanya Malhotra", phoneNumber = "97000-22222", photoPath = null),
                            Contact(name = "Vaani Kapoor", phoneNumber = "91122-33445", photoPath = null),

                            // More actors from all industries
                            Contact(name = "Naga Chaitanya", phoneNumber = "93987-65432", photoPath = null),
                            Contact(name = "Kajal Aggarwal", phoneNumber = "93234-65432", photoPath = null),
                            Contact(name = "Sai Pallavi", phoneNumber = "97765-43210", photoPath = null),
                            Contact(name = "Shraddha Kapoor", phoneNumber = "93987-87654", photoPath = null),
                            Contact(name = "Shruti Haasan", phoneNumber = "96000-33333", photoPath = null),
                            Contact(name = "Ileana D'Cruz", phoneNumber = "92233-34567", photoPath = null),
                            Contact(name = "Brahmanandam", phoneNumber = "97777-76543", photoPath = null),
                            Contact(name = "Sushant Singh Rajput", phoneNumber = "93234-87654", photoPath = null),
                            Contact(name = "Siddharth Malhotra", phoneNumber = "99234-56789", photoPath = null),
                            Contact(name = "Aditya Roy Kapoor", phoneNumber = "98777-23456", photoPath = null),

                            // Continuing with more names
                            Contact(name = "Parineeti Chopra", phoneNumber = "96123-76543", photoPath = null),
                            Contact(name = "Ayushmann Khurrana", phoneNumber = "94444-32123", photoPath = null),
                            Contact(name = "Janhvi Kapoor", phoneNumber = "98877-98765", photoPath = null),
                            Contact(name = "Kiara Advani", phoneNumber = "93332-12345", photoPath = null),
                            Contact(name = "Bhumi Pednekar", phoneNumber = "97765-43211", photoPath = null),
                            Contact(name = "Vicky Kaushal", phoneNumber = "98654-98765", photoPath = null),
                            Contact(name = "Taapsee Pannu", phoneNumber = "93332-11111", photoPath = null),
                            Contact(name = "Ananya Panday", phoneNumber = "91999-76543", photoPath = null),
                            Contact(name = "Sidharth Malhotra", phoneNumber = "93332-98765", photoPath = null),
                            Contact(name = "Radhika Apte", phoneNumber = "97000-23456", photoPath = null)
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
