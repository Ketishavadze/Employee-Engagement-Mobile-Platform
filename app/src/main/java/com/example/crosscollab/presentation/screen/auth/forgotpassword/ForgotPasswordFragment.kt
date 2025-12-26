package com.example.crosscollab.presentation.screen.auth.forgotpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.crosscollab.databinding.FragmentForgotPasswordBinding
import com.example.crosscollab.presentation.common.BaseFragment
import kotlinx.coroutines.launch

class ForgotPasswordFragment :
    BaseFragment<FragmentForgotPasswordBinding>(
        FragmentForgotPasswordBinding::inflate
    ) {

    private val viewModel: ForgotPasswordViewModel by viewModels()

    override fun start() {

        binding.btnSendResetLink.setOnClickListener {
            viewModel.onEvent(
                ForgotPasswordContract.Event.SendResetLinkClicked
            )
        }

        binding.llBackToSignIn.setOnClickListener {
            viewModel.onEvent(
                ForgotPasswordContract.Event.BackToSignInClicked
            )
        }

        observeState()
        observeEffects()
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.state.collect { state ->
                binding.progressBar.visibility =
                    if (state.isLoading) View.VISIBLE else View.GONE

                binding.btnSendResetLink.isEnabled = !state.isLoading
            }
        }
    }

    private fun observeEffects() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.sideEffect.collect { effect ->
                when (effect) {
                    ForgotPasswordContract.Effect.NavigateBackToSignIn -> {
                        // popBackStack() or navigate to Login
                    }
                    is ForgotPasswordContract.Effect.ShowToast -> {
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
