import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crosscollab.R

// NotificationsFragment.kt or NotificationsActivity.kt

class NotificationsFragment : Fragment() {

    private lateinit var rvNewNotifications: RecyclerView
    private lateinit var rvEarlierNotifications: RecyclerView
    private lateinit var tvNewHeader: TextView
    private lateinit var tvEarlierHeader: TextView

    private lateinit var newNotificationsAdapter: NotificationsAdapter
    private lateinit var earlierNotificationsAdapter: NotificationsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews(view)
        setupRecyclerViews()
        loadNotifications()
    }

    private fun setupViews(view: View) {
        rvNewNotifications = view.findViewById(R.id.rvNewNotifications)
        rvEarlierNotifications = view.findViewById(R.id.rvEarlierNotifications)
        tvNewHeader = view.findViewById(R.id.tvNewHeader)
        tvEarlierHeader = view.findViewById(R.id.tvEarlierHeader)
    }

    private fun setupRecyclerViews() {
        // Setup NEW notifications RecyclerView
        newNotificationsAdapter = NotificationsAdapter(showUnreadIndicator = true)
        rvNewNotifications.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = newNotificationsAdapter
        }

        // Setup EARLIER notifications RecyclerView
        earlierNotificationsAdapter = NotificationsAdapter(showUnreadIndicator = false)
        rvEarlierNotifications.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = earlierNotificationsAdapter
        }
    }

    private fun loadNotifications() {
        // Separate your notifications into NEW and EARLIER
        val allNotifications = getNotifications() // Your method to get notifications

        val newNotifications = allNotifications.filter { it.isNew }
        val earlierNotifications = allNotifications.filter { !it.isNew }

        // Update NEW section
        if (newNotifications.isNotEmpty()) {
            tvNewHeader.visibility = View.VISIBLE
            newNotificationsAdapter.submitList(newNotifications)
        } else {
            tvNewHeader.visibility = View.GONE
        }

        // Update EARLIER section
        if (earlierNotifications.isNotEmpty()) {
            tvEarlierHeader.visibility = View.VISIBLE
            earlierNotificationsAdapter.submitList(earlierNotifications)
        } else {
            tvEarlierHeader.visibility = View.GONE
        }
    }

    private fun getNotifications(): List<Notification> {
        // Your notification data source
        return listOf(
            Notification(
                id = 1,
                title = "Registration Confirmed: You are now registered for the 'Leadership Workshop: Effective Communication'.",
                time = "15 minutes ago",
                icon = R.drawable.ic_calendar,
                isNew = true,
                isUnread = true
            ),
            Notification(
                id = 2,
                title = "Event Reminder: 'Annual Team Building Summit' starts in 24 hours. Don't forget to join!",
                time = "1 hour ago",
                icon = R.drawable.ic_notifications,
                isNew = true,
                isUnread = true
            ),
            Notification(
                id = 3,
                title = "Event Update: The location for 'Happy Friday: Game Night' has been changed to the Recreation Lounge.",
                time = "Yesterday",
                icon = R.drawable.ic_info,
                isNew = false,
                isUnread = false
            ),
            Notification(
                id = 4,
                title = "Waitlist Update: A spot has opened up for 'Tech Talk: AI in Business Applications'. You have been automatically registered.",
                time = "2 days ago",
                icon = R.drawable.ic_person,
                isNew = false,
                isUnread = false
            ),
            Notification(
                id = 5,
                title = "Cancellation: Your registration for the 'Wellness Wednesday: Yoga' has been successfully cancelled.",
                time = "Dec 12, 2025",
                icon = R.drawable.ic_calendar,
                isNew = false,
                isUnread = false
            )
        )
    }
}

// Notification data class
data class Notification(
    val id: Int,
    val title: String,
    val time: String,
    val icon: Int,
    val isNew: Boolean,
    val isUnread: Boolean
)

// NotificationsAdapter
class NotificationsAdapter(
    private val showUnreadIndicator: Boolean = false
) : RecyclerView.Adapter<NotificationsAdapter.NotificationViewHolder>() {

    private var notifications = listOf<Notification>()

    fun submitList(newNotifications: List<Notification>) {
        notifications = newNotifications
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_notification, parent, false)
        return NotificationViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bind(notifications[position], showUnreadIndicator)
    }

    override fun getItemCount() = notifications.size

    class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvNotificationTitle)
        private val tvTime: TextView = itemView.findViewById(R.id.tvNotificationTime)
        private val ivIcon: ImageView = itemView.findViewById(R.id.ivNotificationIcon)
        private val viewUnreadIndicator: View = itemView.findViewById(R.id.viewUnreadIndicator)

        fun bind(notification: Notification, showUnread: Boolean) {
            tvTitle.text = notification.title
            tvTime.text = notification.time
            ivIcon.setImageResource(notification.icon)

            // Show unread indicator only for NEW notifications
            viewUnreadIndicator.visibility = if (showUnread && notification.isUnread) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }
}