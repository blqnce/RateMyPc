package movil.ratemypc.ui.screens.perfil

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import movil.ratemypc.data.repository.AuthRepository

import javax.inject.Inject

// ViewModel para la pantalla de Perfil de usuario.
// Proporciona acceso a la información del usuario actual y gestiona la navegación por pestañas y el cierre de sesión.
@HiltViewModel
class PerfilViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PerfilState(
        email = authRepository.currentUser?.email ?: ""
    ))
    val uiState: StateFlow<PerfilState> = _uiState.asStateFlow()

    // Actualiza el índice de la pestaña seleccionada en el perfil.
    fun onTabSelected(index: Int) {
        _uiState.update { it.copy(selectedTabIndex = index) }
    }

    // Cierra la sesión del usuario actual.
    fun onSignOut(onSignOutComplete: () -> Unit) {
        authRepository.signOut()
        onSignOutComplete()
    }
}
