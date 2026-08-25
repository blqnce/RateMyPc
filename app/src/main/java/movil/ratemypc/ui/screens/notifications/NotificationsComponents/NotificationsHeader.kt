package movil.ratemypc.ui.screens.notifications.NotificationsComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Badge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import movil.ratemypc.R

@Composable
fun NotificationsHeader(
    modifier: Modifier = Modifier,
    unreadCount: Int,
    onMarkAllRead: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(R.string.notifications_title),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            if (unreadCount > 0) {
                Box(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .size(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Badge { Text(unreadCount.toString()) }
                }
            }
        }
        TextButton(onClick = onMarkAllRead, enabled = unreadCount > 0) {
            Text(stringResource(R.string.notifications_mark_all_read))
        }
    }
}