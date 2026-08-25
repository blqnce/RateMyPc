package movil.ratemypc.ui.screens.notifications.NotificationsComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import movil.ratemypc.R
import movil.ratemypc.data.NotificationItem
import movil.ratemypc.data.NotificationType
import movil.ratemypc.ui.theme.NotificationGreen
import movil.ratemypc.ui.theme.NotificationOrange
import movil.ratemypc.ui.theme.NotificationPink
import movil.ratemypc.ui.theme.NotificationPurple
import movil.ratemypc.ui.theme.MainBlue

@Composable
fun NotificationItemCard(
    modifier: Modifier = Modifier,
    notification: NotificationItem,
    onClick: () -> Unit
) {
    val icon = when (notification.type) {
        NotificationType.FOLLOW -> Icons.Outlined.PersonAdd
        NotificationType.COMMENT -> Icons.Outlined.ChatBubbleOutline
        NotificationType.LIKE -> Icons.Outlined.FavoriteBorder
    }
    val iconDescription = when (notification.type) {
        NotificationType.FOLLOW -> stringResource(R.string.notifications_type_follow)
        NotificationType.COMMENT -> stringResource(R.string.notifications_type_comment)
        NotificationType.LIKE -> stringResource(R.string.notifications_type_like)
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (notification.isRead) {
                MaterialTheme.colorScheme.surface
            } else {
                MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
            }
        )
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(contentAlignment = Alignment.BottomEnd) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(avatarColor(notification.avatarColorKey), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = notification.avatarInitial,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
                Icon(
                    imageVector = icon,
                    contentDescription = iconDescription,
                    modifier = Modifier
                        .size(20.dp)
                        .background(MaterialTheme.colorScheme.surface, CircleShape)
                        .padding(3.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                Text(
                    text = notification.userName,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = notification.message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = notification.timestamp,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.outline
                )
            }
            if (!notification.isRead) {
                Box(
                    modifier = Modifier
                        .size(9.dp)
                        .background(MaterialTheme.colorScheme.primary, CircleShape)
                )
            }
        }
    }
}

private fun avatarColor(colorKey: String): Color = when (colorKey) {
    "orange" -> NotificationOrange
    "green" -> NotificationGreen
    "purple" -> NotificationPurple
    "pink" -> NotificationPink
    else -> MainBlue
}