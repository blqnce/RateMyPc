package movil.ratemypc.data.local

import movil.ratemypc.data.NotificationItem
import movil.ratemypc.data.NotificationType

object LocalNotificationsProvider {
    val notifications = listOf(
        NotificationItem(
            id = "notification-1",
            type = NotificationType.FOLLOW,
            userName = "BuildMaster_X",
            message = "comenzó a seguirte",
            timestamp = "Hace 5 min",
            avatarInitial = "B",
            avatarColorKey = "cyan",
            isRead = false
        ),
        NotificationItem(
            id = "notification-2",
            type = NotificationType.COMMENT,
            userName = "PCBuilder_Mike",
            message = "comentó tu reseña de GeForce RTX 4080 Super",
            timestamp = "Hace 32 min",
            avatarInitial = "P",
            avatarColorKey = "orange",
            isRead = false
        ),
        NotificationItem(
            id = "notification-3",
            type = NotificationType.LIKE,
            userName = "GamerDad_TX",
            message = "marcó como útil tu reseña",
            timestamp = "Hace 1 h",
            avatarInitial = "G",
            avatarColorKey = "green",
            isRead = false
        ),
        NotificationItem(
            id = "notification-4",
            type = NotificationType.FOLLOW,
            userName = "SilentPC_Pro",
            message = "comenzó a seguirte",
            timestamp = "Ayer",
            avatarInitial = "S",
            avatarColorKey = "purple",
            isRead = true
        ),
        NotificationItem(
            id = "notification-5",
            type = NotificationType.LIKE,
            userName = "TechFan_92",
            message = "marcó como útil tu reseña",
            timestamp = "Ayer",
            avatarInitial = "T",
            avatarColorKey = "pink",
            isRead = true
        ),
        NotificationItem(
            id = "notification-6",
            type = NotificationType.COMMENT,
            userName = "HardwareNerd",
            message = "respondió a tu comentario sobre Ryzen 9 7950X3D",
            timestamp = "Hace 2 días",
            avatarInitial = "H",
            avatarColorKey = "cyan",
            isRead = false
        ),
        NotificationItem(
            id = "notification-7",
            type = NotificationType.LIKE,
            userName = "LauraBuilds",
            message = "marcó como útil tu reseña de memoria RAM",
            timestamp = "Hace 2 días",
            avatarInitial = "L",
            avatarColorKey = "orange",
            isRead = true
        ),
        NotificationItem(
            id = "notification-8",
            type = NotificationType.FOLLOW,
            userName = "GPUHunter",
            message = "comenzó a seguirte",
            timestamp = "Hace 3 días",
            avatarInitial = "G",
            avatarColorKey = "green",
            isRead = false
        ),
        NotificationItem(
            id = "notification-9",
            type = NotificationType.COMMENT,
            userName = "BuildMaster_X",
            message = "comentó tu build 4K Ultra Gaming Rig",
            timestamp = "Hace 4 días",
            avatarInitial = "B",
            avatarColorKey = "purple",
            isRead = true
        ),
        NotificationItem(
            id = "notification-10",
            type = NotificationType.LIKE,
            userName = "PCSetupDaily",
            message = "marcó como útil tu build",
            timestamp = "Hace 5 días",
            avatarInitial = "P",
            avatarColorKey = "pink",
            isRead = true
        )
    )
}