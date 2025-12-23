package com.example.crosscollab.presentation.screen.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.crosscollab.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        observeState()
        observeEffects()
    }

    private fun setupViews() {
        binding.etEmail.addTextChangedListener { text ->
            viewModel.onEvent(LoginContract.Event.OnEmailChanged(text.toString()))
        }

        binding.etPassword.addTextChangedListener { text ->
            viewModel.onEvent(LoginContract.Event.OnPasswordChanged(text.toString()))
        }

        binding.cbRememberMe.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onEvent(LoginContract.Event.OnRememberMeChanged(isChecked))
        }

        binding.btnSignIn.setOnClickListener {
            viewModel.onEvent(LoginContract.Event.OnSignInClicked)
        }

        binding.tvForgotPassword.setOnClickListener {
            viewModel.onEvent(LoginContract.Event.OnForgotPasswordClicked)
        }

        binding.tvSignUp.setOnClickListener {
            viewModel.onEvent(LoginContract.Event.OnSignUpClicked)
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                updateUI(state)
            }
        }
    }

    private fun updateUI(state: LoginContract.State) {
        // Update loading state
        binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        binding.btnSignIn.isEnabled = !state.isLoading && state.isSignInEnabled
        binding.btnSignIn.alpha = if (state.isSignInEnabled) 1f else 0.5f

        // Update error messages
        binding.tilEmail.error = state.emailError
        binding.tilPassword.error = state.passwordError

        // Update remember me
        if (binding.cbRememberMe.isChecked != state.rememberMe) {
            binding.cbRememberMe.isChecked = state.rememberMe
        }
    }

    private fun observeEffects() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.effect.collect { effect ->
                handleEffect(effect)
            }
        }
    }

    private fun handleEffect(effect: LoginContract.Effect) {
        when (effect) {
            is LoginContract.Effect.NavigateToHome -> {
                // Navigate to home screen
                // findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                Toast.makeText(requireContext(), "Login Successful!", Toast.LENGTH_SHORT).show()
            }

            is LoginContract.Effect.NavigateToForgotPassword -> {
                // Navigate to forgot password screen
                // findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
                Toast.makeText(requireContext(), "Navigate to Forgot Password", Toast.LENGTH_SHORT).show()
            }

            is LoginContract.Effect.NavigateToSignUp -> {
                // Navigate to sign up screen
                // findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
                Toast.makeText(requireContext(), "Navigate to Sign Up", Toast.LENGTH_SHORT).show()
            }

            is LoginContract.Effect.ShowError -> {
                Toast.makeText(requireContext(), effect.message, Toast.LENGTH_LONG).show()
            }

            is LoginContract.Effect.ShowToast -> {
                Toast.makeText(requireContext(), effect.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}