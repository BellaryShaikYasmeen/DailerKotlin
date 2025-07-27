import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.dailerkotlin.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RecentCallAdapter(
    private var calls: List<Call>
): RecyclerView.Adapter<RecentCallAdapter.CallViewHolder>() {

    inner class CallViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icon: ImageView = view.findViewById(R.id.callTypeIcon)
        val number: TextView = view.findViewById(R.id.phoneNumber)
        val details: TextView = view.findViewById(R.id.callDetails)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CallViewHolder(
            LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recent_call, parent, false))

    override fun onBindViewHolder(holder: CallViewHolder, position: Int) {
        val call = calls[position]
        val iconRes = when (call.callType) {
            CallType.INCOMING.toString() -> R.drawable.ic_call_received
            CallType.OUTGOING.toString() -> R.drawable.ic_call_made
            CallType.MISSED.toString() -> R.drawable.ic_call_missed
            else -> {}
        }
//        holder.icon.setImageResource(iconRes)
        holder.number.text = call.phoneNumber
        holder.details.text = SimpleDateFormat("MMM d, HH:mm", Locale.getDefault())
            .format(Date(call.timestamp))
    }

    override fun getItemCount() = calls.size

    fun updateList(newList: List<Call>) {
        calls = newList
        notifyDataSetChanged()
    }
}
