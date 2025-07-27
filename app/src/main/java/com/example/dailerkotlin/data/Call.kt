import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "calls")
data class Call(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val contactName: String,
    val phoneNumber: String,
    val callType: String, // e.g., "Incoming", "Outgoing", "Missed"
    val timestamp: Long
)
enum class CallType { INCOMING, OUTGOING, MISSED }
