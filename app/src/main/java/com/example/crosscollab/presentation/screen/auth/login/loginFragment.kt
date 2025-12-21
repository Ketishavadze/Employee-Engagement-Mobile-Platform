package com.example.crosscollab.presentation.screen.auth.login

import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.crosscollab.R
import com.example.crosscollab.databinding.FragmentLoginBinding
import com.example.crosscollab.presentation.common.BaseFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(
    FragmentLoginBinding::inflate
) {

    private val viewModel: LoginViewModel by viewModels()

    override fun start() {
        setupListeners()
        observeState()
        observeSideEffects()
    }

    private fun setupListeners() {
        with(binding) {
            // Text change listeners
            etEmail.addTextChangedListener {
                viewModel.onEvent(LoginEvent.OnEmailChanged(it.toString()))
            }

            etPassword.addTextChangedListener {
                viewModel.onEvent(LoginEvent.OnPasswordChanged(it.toString()))
            }

            // Sign In Button
            btnSignIn.setOnClickListener {
                val email = etEmail.text.toString().trim()
                val password = etPassword.text.toString().trim()
                viewModel.onEvent(LoginEvent.OnLoginClicked(email, password))
            }

            // Navigate to Sign Up
            tvSignUp.setOnClickListener {
                viewModel.onEvent(LoginEvent.OnSignUpClicked)
            }

            // Navigate to Forgot Password
            tvForgotPassword.setOnClickListener {
                viewModel.onEvent(LoginEvent.OnForgotPasswordClicked)
            }
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                renderState(state)
            }
        }
    }

    private fun renderState(state: LoginState) {
        with(binding) {
            // Loading state
            progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
            btnSignIn.text = if (state.isLoading) "" else "Sign In"
            btnSignIn.isEnabled = !state.isLoading
            etEmail.isEnabled = !state.isLoading
            etPassword.isEnabled = !state.isLoading

            // Field errors
            tilEmail.error = state.emailError
            tilPassword.error = state.passwordError
        }
    }

    private fun observeSideEffects() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.sideEffect.collectLatest { sideEffect ->
                handleSideEffect(sideEffect)
            }
        }
    }

    private fun handleSideEffect(sideEffect: LoginSideEffect) {
        when (sideEffect) {
            is LoginSideEffect.NavigateToHome -> {
                // TODO: Replace with actual home navigation action
                // findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                showSnackbar("Login successful!", isSuccess = true)
            }
            is LoginSideEffect.NavigateToRegister -> {
                // findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
                showSnackbar("Navigate to Register - Add navigation action")
            }
            is LoginSideEffect.NavigateToForgotPassword -> {
                // findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
                showSnackbar("Navigate to Forgot Password - Add navigation action")
            }
            is LoginSideEffect.ShowError -> {
                showSnackbar(sideEffect.message, isError = true)
            }
            is LoginSideEffect.ShowSuccess -> {
                showSnackbar(sideEffect.message, isSuccess = true)
            }
        }
    }

    private fun showSnackbar(
        message: String,
        isError: Boolean = false,
        isSuccess: Boolean = false
    ) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .apply {
                when {
                    isError -> setBackgroundTint(resources.getColor(R.color.error, null))
                    isSuccess -> setBackgroundTint(resources.getColor(R.color.success, null))
                }
            }
            .show()
    }
}