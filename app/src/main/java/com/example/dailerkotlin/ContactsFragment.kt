package com.example.dailerkotlin

import AppDatabase
import Contact
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class ContactsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ContactAdapter
    private lateinit var searchEditText: EditText
    private lateinit var db: AppDatabase
    private var allContacts = listOf<Contact>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contacts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.contactsRecyclerView)
        searchEditText = view.findViewById(R.id.searchEditText)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        db = AppDatabase.getDatabase(requireContext())

        loadContacts()

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val filtered = allContacts.filter {
                    it.name.contains(s.toString(), ignoreCase = true) ||
                            it.phoneNumber.contains(s.toString(), ignoreCase = true)
                }
                adapter.updateList(filtered)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun loadContacts() {
        adapter = ContactAdapter(emptyList(), onToggleFavourite = { updatedContact ->
            lifecycleScope.launch {
                db.contactDao().update(updatedContact)
            }
        }, R.layout.item_contact)

        recyclerView.adapter = adapter

        lifecycleScope.launch {
            db.contactDao().getAllContactsFlow().collect { contacts ->
                allContacts = contacts
                val filtered = contacts.filter {
                    val query = searchEditText.text.toString()
                    it.name.contains(query, ignoreCase = true) ||
                            it.phoneNumber.contains(query, ignoreCase = true)
                }
                adapter.updateList(filtered)
            }
        }
    }

}

