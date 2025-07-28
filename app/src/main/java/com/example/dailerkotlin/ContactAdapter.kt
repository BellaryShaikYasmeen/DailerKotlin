package com.example.dailerkotlin
import Contact
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
class ContactAdapter(
    private var contactList: List<Contact>,
    private val onToggleFavourite: (Contact) -> Unit,
    private val layoutResId: Int // New parameter for layout resource ID
) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val contactAvatar: ImageView = itemView.findViewById(R.id.contactAvatar)
        val initialCircle: TextView = itemView.findViewById(R.id.initialCircle)
        val favoriteButton: ImageButton = itemView.findViewById(R.id.favoriteButton)
        val contactName: TextView = itemView.findViewById(R.id.contactName)

        init {
            favoriteButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val contact = contactList[position]
                    contact.isFavourite = !contact.isFavourite
                    onToggleFavourite(contact) // Notify that favorite status changed
                    notifyItemChanged(position) // Update the view
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        // Inflate the layout dynamically based on the provided layoutResId
        val itemView = LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
        return ContactViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contactList[position]

        // Set name
        holder.contactName.text = contact.name

        // Set avatar (handle case where photoPath is null or empty)
        if (!contact.photoPath.isNullOrEmpty()) {
            Glide.with(holder.itemView.context)
                .load(contact.photoPath) // Use Glide or Picasso to load images
                .placeholder(R.drawable.ic_avatar_placeholder) // Placeholder for image loading
                .into(holder.contactAvatar)

            holder.contactAvatar.visibility = View.VISIBLE
            holder.initialCircle.visibility = View.GONE
        } else {
            holder.contactAvatar.visibility = View.GONE
            holder.initialCircle.visibility = View.VISIBLE
            holder.initialCircle.text = contact.name.take(2).uppercase() // Display initials
        }

        // Set favorite button image based on favorite status
        if (contact.isFavourite) {
            holder.favoriteButton.setImageResource(R.drawable.ic_star_filled)  // Or other icon for filled
        } else {
            holder.favoriteButton.setImageResource(R.drawable.ic_star_outline)  // Or other icon for empty
        }
    }

    override fun getItemCount(): Int = contactList.size

    // Method to update the list
    fun updateList(newContacts: List<Contact>) {
        contactList = newContacts
        notifyDataSetChanged()
    }
}
