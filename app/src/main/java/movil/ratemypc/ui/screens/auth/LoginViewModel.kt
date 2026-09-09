package movil.ratemypc.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import movil.ratemypc.data.repository.AuthRepository

import javax.inject.Inject

// ViewModel para la pantalla de inicio de sesión.
// Se encarga de la validación de credenciales y la interacción con Firebase Auth.
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginState())
    val uiState: StateFlow<LoginState> = _uiState.asStateFlow()

    // Actualiza el correo electrónico en el estado y limpia errores previos.
    fun onEmailChange(newValue: String) {
        _uiState.update { it.copy(email = newValue, emailError = null) }
    }

    // Actualiza la contraseña en el estado y limpia errores previos.
    fun onPasswordChange(newValue: String) {
        _uiState.update { it.copy(password = newValue, passwordError = null) }
    }

    // Alterna la visibilidad del texto de la contraseña.
    fun onTogglePasswordVisibility() {
        _uiState.update { it.copy(passwordVisible = !it.passwordVisible) }
    }

    // Valida que los campos de entrada cumplan con los requisitos básicos.
    private fun validate(): Boolean {
        val state = _uiState.value
        val emailError = if (state.email.isBlank() || !state.email.contains("@")) "Ingresa un correo válido" else null
        val passwordError = if (state.password.length < 6) "Mínimo 6 caracteres" else null

        _uiState.update {
            it.copy(
                emailError = emailError,
                passwordError = passwordError
            )
        }

        return emailError == null && passwordError == null
    }

    // Ejecuta el proceso de inicio de sesión con Firebase.
    fun onLoginClick(onLoginSuccess: () -> Unit) {
        if (!validate()) return

        viewModelScope.launch {
            _uiState.update { it.copy(error = null) }
            try {
                val state = _uiState.value
                authRepository.signIn(state.email, state.password)
                onLoginSuccess()
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message.toString()) }
            }
        }
    }
}
