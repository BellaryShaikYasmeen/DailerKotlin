import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dailerkotlin.R
import com.example.dailerkotlin.RecentCallAdapter
import kotlinx.coroutines.launch

class RecentsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecentCallAdapter // New adapter for Call type
    private lateinit var db: AppDatabase

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_recents, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recentsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        db = AppDatabase.getDatabase(requireContext())
        adapter = RecentCallAdapter(emptyList())
        recyclerView.adapter = adapter

        lifecycleScope.launch {
            db.callDao().getRecentCallsFlow().collect { calls ->
                adapter.updateList(calls)
            }
        }
    }
}
