import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dailerkotlin.ContactAdapter
import com.example.dailerkotlin.R
import kotlinx.coroutines.launch

class FavoritesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ContactAdapter
    private lateinit var db: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_favorites, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.favoritesRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 5) // Adjust column count

        db = AppDatabase.getDatabase(requireContext())

        // Initialize adapter with an empty list initially
        adapter = ContactAdapter(emptyList(), onToggleFavourite = { updatedContact ->
            lifecycleScope.launch {
                db.contactDao().update(updatedContact)
                refreshFavorites()  // Refresh favorites after updating
            }
        }, R.layout.fev_contact)

        recyclerView.adapter = adapter

        // Observe real-time updates from the database
        lifecycleScope.launch {
            db.contactDao().getAllContactsFlow().collect { contacts ->
                // Filter the contacts to show only favorites
                val favoriteContacts = contacts.filter { it.isFavourite }
                adapter.updateList(favoriteContacts) // Update adapter list
            }
        }
    }

    private suspend fun refreshFavorites() {
        // Get the latest favorite contacts from the database
        val favoriteContacts = db.contactDao().getAllContacts().filter { it.isFavourite }
        adapter.updateList(favoriteContacts) // Update the adapter with the refreshed list
    }
}
