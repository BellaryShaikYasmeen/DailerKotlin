import androidx.room.*
import kotlinx.coroutines.flow.Flow
@Dao
interface ContactDao {

    @Query("SELECT * FROM contacts")
    suspend fun getAllContacts(): List<Contact>  // <-- Already used in your code

    @Query("SELECT * FROM contacts")
    fun getAllContactsFlow(): kotlinx.coroutines.flow.Flow<List<Contact>>  // <-- For real-time updates

    @Update
    suspend fun update(contact: Contact)
}
