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

// ViewModel para la pantalla de registro.
// Gestiona el proceso de creación de nuevas cuentas de usuario a través de Firebase Auth.
@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterState())
    val uiState: StateFlow<RegisterState> = _uiState.asStateFlow()

    // Actualiza el nombre completo en el estado.
    fun onNameChange(newValue: String) {
        _uiState.update { it.copy(name = newValue, nameError = null) }
    }

    // Actualiza el correo electrónico en el estado.
    fun onEmailChange(newValue: String) {
        _uiState.update { it.copy(email = newValue, emailError = null) }
    }

    // Actualiza la contraseña en el estado.
    fun onPasswordChange(newValue: String) {
        _uiState.update { it.copy(password = newValue, passwordError = null) }
    }

    // Actualiza la confirmación de la contraseña en el estado.
    fun onConfirmPasswordChange(newValue: String) {
        _uiState.update { it.copy(confirmPassword = newValue, confirmError = null) }
    }

    // Alterna la visibilidad del campo de contraseña.
    fun onTogglePasswordVisibility() {
        _uiState.update { it.copy(passwordVisible = !it.passwordVisible) }
    }

    // Alterna la visibilidad del campo de confirmación de contraseña.
    fun onToggleConfirmVisibility() {
        _uiState.update { it.copy(confirmVisible = !it.confirmVisible) }
    }

    // Realiza validaciones locales sobre los campos de registro.
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

    // Ejecuta el registro del usuario en Firebase.
    fun onRegisterClick(onRegistered: () -> Unit) {
        if (!validate()) return
        
        viewModelScope.launch {
            _uiState.update { it.copy(error = null) }
            try {
                val state = _uiState.value
                authRepository.signUp(state.email, state.password)
                onRegistered()
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message.toString()) }
            }
        }
    }
}
