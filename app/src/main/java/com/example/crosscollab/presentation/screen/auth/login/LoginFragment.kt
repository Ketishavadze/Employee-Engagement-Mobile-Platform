package com.example.crosscollab.presentation.screen.auth.login

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.crosscollab.databinding.FragmentLoginBinding
import com.example.crosscollab.presentation.common.BaseFragment


class LoginFragment :
    BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()

    override fun start() {

        binding.btnSignIn.setOnClickListener {
            viewModel.onEvent(LoginContract.Event.SignInClicked)
        }

        binding.tvSignUp.setOnClickListener {
            viewModel.onEvent(LoginContract.Event.SignUpClicked)
        }

        binding.tvForgotPassword.setOnClickListener {
            viewModel.onEvent(LoginContract.Event.ForgotPasswordClicked)
        }

        binding.cbRememberMe.setOnCheckedChangeListener { _, checked ->
            viewModel.onEvent(LoginContract.Event.RememberMeChanged(checked))
        }

        observeState()
        observeEffects()
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.state.collect { state ->
                binding.progressBar.visibility =
                    if (state.isLoading) View.VISIBLE else View.GONE

                binding.btnSignIn.isEnabled = !state.isLoading
            }
        }
    }

    private fun observeEffects() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.sideEffect.collect { effect ->
                when (effect) {
                    LoginContract.Effect.NavigateToHome -> {
                        // navigate to home
                    }
                    LoginContract.Effect.NavigateToRegister -> {
                        // navigate to register
                    }
                    LoginContract.Effect.NavigateToForgotPassword -> {
                        // navigate to forgot password
                    }
                    is LoginContract.Effect.ShowToast -> {
                        Toast.makeText(
                            requireContext(),
                            effect.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}
