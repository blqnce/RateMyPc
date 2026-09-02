package movil.ratemypc.ui.screens.auth

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RegisterViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterState())
    val uiState: StateFlow<RegisterState> = _uiState.asStateFlow()

    fun onNameChange(newValue: String) {
        _uiState.update { it.copy(name = newValue, nameError = null) }
    }

    fun onEmailChange(newValue: String) {
        _uiState.update { it.copy(email = newValue, emailError = null) }
    }

    fun onPasswordChange(newValue: String) {
        _uiState.update { it.copy(password = newValue, passwordError = null) }
    }

    fun onConfirmPasswordChange(newValue: String) {
        _uiState.update { it.copy(confirmPassword = newValue, confirmError = null) }
    }

    fun onTogglePasswordVisibility() {
        _uiState.update { it.copy(passwordVisible = !it.passwordVisible) }
    }

    fun onToggleConfirmVisibility() {
        _uiState.update { it.copy(confirmVisible = !it.confirmVisible) }
    }

    private fun validate(): Boolean {
        val state = _uiState.value
        val nameError = if (state.name.isBlank()) "Ingresa tu nombre" else null
        val emailError = if (state.email.isBlank() || !state.email.contains("@")) "Ingresa un correo válido" else null
        val passwordError = when {
            state.password.length < 6 -> "Mínimo 6 caracteres"
            !state.password.any { it.isDigit() } -> "Debe contener al menos un número"
            !state.password.any { it.isUpperCase() } -> "Debe contener al menos una mayúscula"
            else -> null
        }
        val confirmError = if (state.confirmPassword != state.password) "Las contraseñas no coinciden" else null

        _uiState.update {
            it.copy(
                nameError = nameError,
                emailError = emailError,
                passwordError = passwordError,
                confirmError = confirmError
            )
        }

        return nameError == null && emailError == null && passwordError == null && confirmError == null
    }

    fun onRegisterClick(onRegistered: () -> Unit) {
        if (!validate()) return
        _uiState.update { it.copy(isLoading = true) }
        // Simular registro
        _uiState.update { it.copy(isLoading = false) }
        onRegistered()
    }
}
