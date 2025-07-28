package com.example.dailerkotlin.Adapter

import Contact
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dailerkotlin.R
class ContactAdapter(
    private var contacts: List<Contact>,
    private val onToggleFavourite: (Contact) -> Unit,
    private val layoutResId: Int // pass layout resource here
) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    inner class ContactViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.contactName)
        val initial: TextView = view.findViewById(R.id.initialCircle)
        val favButton: ImageButton = view.findViewById(R.id.favoriteButton)
        val icon:ImageView=view.findViewById(R.id.contactAvatar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contacts[position]
        holder.name.text = if (contact.name.isNotEmpty()) contact.name else contact.phoneNumber
        holder.initial.text = contact.name.firstOrNull()?.toString() ?: "#"
        holder.icon.drawable


        val iconRes = if (contact.isFavourite) R.drawable.ic_star_filled else R.drawable.ic_star_outline
        holder.favButton.setImageResource(iconRes)

        holder.favButton.setOnClickListener {
            onToggleFavourite(contact.copy(isFavourite = !contact.isFavourite))
        }
        if (position == 2) {
            holder.icon.setImageResource(R.drawable.jacob )// Use a custom image/icon here
        }
    }

    override fun getItemCount(): Int = contacts.size

    fun updateList(filtered: List<Contact>) {
        contacts = filtered
        notifyDataSetChanged()
    }
}
