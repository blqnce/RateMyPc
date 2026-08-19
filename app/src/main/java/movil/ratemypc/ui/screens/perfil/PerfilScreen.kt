package movil.ratemypc.ui.screens.perfil

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import movil.ratemypc.ui.screens.perfil.PerfilComponents.Avatar
import movil.ratemypc.ui.screens.perfil.PerfilComponents.BuildCard
import movil.ratemypc.ui.screens.perfil.PerfilComponents.InfoPerfil
import movil.ratemypc.ui.screens.perfil.PerfilComponents.TabPerfil

@Composable
fun PerfilScreen() {
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "PERFIL",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 2.sp
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )
                IconButton(
                    onClick = { /* Settings */ },
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Settings,
                        contentDescription = "Ajustes",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(Modifier.height(16.dp))
                Box(contentAlignment = Alignment.Center) {
                    Avatar()
                }
                Spacer(Modifier.height(16.dp))
            }

            item {
                InfoPerfil(
                    username = "TechWizard92",
                    bio = "Entusiasta de PCs - Desde 2022",
                    buildsCount = "12",
                    reviewsCount = "47",
                    likesCount = "2.4k",
                    followersCount = "831",
                    followingCount = "215"
                ) { /* Handle Follow */ }
                Spacer(Modifier.height(24.dp))
            }

            item {
                TabPerfil(
                    selectedTabIndex = selectedTabIndex,
                    onTabSelected = { selectedTabIndex = it },
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
                Spacer(Modifier.height(24.dp))
            }

            items(3) {
                BuildCard(
                    title = "Ryzen Boost X670",
                    imageUrl = "https://images.unsplash.com/photo-1587202372775-e229f172b9d7?q=80&w=1000&auto=format&fit=crop",
                    likes = "312",
                    componentsCount = "8",
                    rating = "4.8"
                )
                Spacer(Modifier.height(16.dp))
            }
            
            item {
                Spacer(Modifier.height(80.dp))
            }
        }
    }
}

@Preview
@Composable
fun PerfilScreenComposable() {
    PerfilScreen()
}
