package movil.ratemypc.ui.screens.auth

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginState())
    val uiState: StateFlow<LoginState> = _uiState.asStateFlow()

    fun onEmailChange(newValue: String) {
        _uiState.update { it.copy(email = newValue, emailError = null) }
    }

    fun onPasswordChange(newValue: String) {
        _uiState.update { it.copy(password = newValue, passwordError = null) }
    }

    fun onTogglePasswordVisibility() {
        _uiState.update { it.copy(passwordVisible = !it.passwordVisible) }
    }

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

    fun onLoginClick(onLoginSuccess: () -> Unit) {
        if (!validate()) return
        _uiState.update { it.copy(isLoading = true) }
        // Simulación:
        _uiState.update { it.copy(isLoading = false) }
        onLoginSuccess()
    }
}
