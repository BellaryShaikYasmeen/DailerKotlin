package com.example.dailerkotlin.view
import AppDatabase
import Contact
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dailerkotlin.Adapter.ContactAdapter
import com.example.dailerkotlin.R
import kotlinx.coroutines.launch
class FavoritesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ContactAdapter
    private lateinit var db: AppDatabase
    private var favoriteContacts = listOf<Contact>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_favorites, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.favoritesRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 5) // adjust column count

        db = AppDatabase.getDatabase(requireContext())

        adapter = ContactAdapter(emptyList(), onToggleFavourite = { updatedContact ->
            lifecycleScope.launch {
                db.contactDao().update(updatedContact)
            }
        }, R.layout.item_fav_contact)

        recyclerView.adapter = adapter

        // Observe real-time updates
        lifecycleScope.launch {
            db.contactDao().getAllContactsFlow().collect { contacts ->
                val favorites = contacts.filter { it.isFavourite }
                adapter.updateList(favorites)
            }
        }
    }


    private suspend fun loadFavorites() {
        val favoriteContacts = db.contactDao().getAllContacts().filter { it.isFavourite }
        adapter = ContactAdapter(favoriteContacts, onToggleFavourite = { updatedContact ->
            lifecycleScope.launch {
                db.contactDao().update(updatedContact)
                refreshFavorites()
            }
        }, R.layout.item_fav_contact)
        // Use your favorite layout here
        recyclerView.adapter = adapter
    }


    private suspend fun refreshFavorites() {
        favoriteContacts = db.contactDao().getAllContacts().filter { it.isFavourite }
        adapter.updateList(favoriteContacts)
    }
}
