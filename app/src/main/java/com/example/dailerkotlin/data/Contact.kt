import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class Contact(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val phoneNumber: String,
    var isFavourite: Boolean = false,  // Default value for favourite
    val photoPath: String? = null      // Optional path to the photo (can be null)
)
