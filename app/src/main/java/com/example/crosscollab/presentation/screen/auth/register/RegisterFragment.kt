package com.example.crosscollab.presentation.screen.auth.register


import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.crosscollab.R
import com.example.crosscollab.databinding.FragmentRegisterBinding
import com.example.crosscollab.presentation.common.BaseFragment


class RegisterFragment :
    BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val viewModel: RegisterViewModel by viewModels()

    override fun start() {

        viewModel.onEvent(RegisterContract.Event.LoadDepartments)

        binding.btnSentOTP.setOnClickListener {
            viewModel.onEvent(RegisterContract.Event.SendOtp)
        }

        binding.btnCreateAccount.setOnClickListener {
            viewModel.onEvent(RegisterContract.Event.CreateAccount)
        }

        binding.tvBtnSignIn.setOnClickListener {
            viewModel.onEvent(RegisterContract.Event.SignInClicked)
        }

        observeState()
        observeEffects()
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.state.collect { state ->
                binding.tvExpireTime.text =
                    String.format("00:%02d", state.otpExpirySeconds)

                binding.btnCreateAccount.isEnabled =
                    state.isOtpVerified && state.agreePolicy
            }
        }
    }

    private fun observeEffects() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.sideEffect.collect { effect ->
                when (effect) {
                    RegisterContract.Effect.NavigateToHome -> {
                        // navigate to home
                    }
                    RegisterContract.Effect.NavigateToSignIn -> {
                        // navigate to login
                    }
                    is RegisterContract.Effect.ShowToast -> {
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
