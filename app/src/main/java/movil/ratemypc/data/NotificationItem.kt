package movil.ratemypc.data

enum class NotificationType {
    FOLLOW,
    COMMENT,
    LIKE
}

enum class NotificationFilter {
    ALL,
    FOLLOWS,
    COMMENTS,
    LIKES
}

data class NotificationItem(
    val id: String,
    val type: NotificationType,
    val userName: String,
    val message: String,
    val timestamp: String,
    val avatarInitial: String,
    val avatarColorKey: String,
    val isRead: Boolean
)