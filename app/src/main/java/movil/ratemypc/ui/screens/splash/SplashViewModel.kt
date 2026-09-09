package movil.ratemypc.ui.screens.splash

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import movil.ratemypc.data.repository.AuthRepository
import javax.inject.Inject

// ViewModel para la pantalla de Splash.
// Determina el estado de autenticación inicial para dirigir al usuario al inicio o al login.
@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _navigateHome = MutableStateFlow(false)
    val navigateHome: StateFlow<Boolean> = _navigateHome

    init {
        checkUser()
    }

    // Verifica si hay un usuario autenticado actualmente en Firebase.
    // Actualiza [navigateHome] según el resultado.
    private fun checkUser() {
        if (authRepository.currentUser != null) {
            _navigateHome.value = true
        } else {
            _navigateHome.value = false
        }
    }
}
