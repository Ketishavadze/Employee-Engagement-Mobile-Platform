package com.example.crosscollab.presentation.screen.auth.register

import android.os.Bundle
import android.text.InputFilter
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.crosscollab.databinding.FragmentRegisterBinding
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupOtpFields()
        setupDepartmentDropdown()
        observeState()
        observeEffects()
    }

    private fun setupViews() {
        // First name
        binding.etFirstName.addTextChangedListener { text ->
            viewModel.onEvent(RegisterContract.Event.OnFirstNameChanged(text.toString()))
        }

        // Last name
        binding.etLastName.addTextChangedListener { text ->
            viewModel.onEvent(RegisterContract.Event.OnLastNameChanged(text.toString()))
        }

        // Email
        binding.etEmail.addTextChangedListener { text ->
            viewModel.onEvent(RegisterContract.Event.OnEmailChanged(text.toString()))
        }

        // Phone number
        binding.etPhoneNumber.addTextChangedListener { text ->
            viewModel.onEvent(RegisterContract.Event.OnPhoneNumberChanged(text.toString()))
        }

        // Send OTP button
        binding.btnSentOTP.setOnClickListener {
            viewModel.onEvent(RegisterContract.Event.OnSendOtpClicked)
        }

        // Resend code
        binding.tvBtnResendCode.setOnClickListener {
            viewModel.onEvent(RegisterContract.Event.OnResendOtpClicked)
        }

        // Password
        binding.etPassword.addTextChangedListener { text ->
            viewModel.onEvent(RegisterContract.Event.OnPasswordChanged(text.toString()))
        }

        // Confirm password
        binding.etConfirmPassword.addTextChangedListener { text ->
            viewModel.onEvent(RegisterContract.Event.OnConfirmPasswordChanged(text.toString()))
        }

        // Password requirements info
        binding.icRequiredPassword.setOnClickListener {
            viewModel.onEvent(RegisterContract.Event.OnPasswordRequirementsClicked)
        }

        binding.icRequiredConfirmPass.setOnClickListener {
            viewModel.onEvent(RegisterContract.Event.OnPasswordRequirementsClicked)
        }

        // Policy checkbox
        binding.checkboxPolicy.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onEvent(RegisterContract.Event.OnPolicyAgreementChanged(isChecked))
        }

        // Create account button
        binding.btnCreateAccount.setOnClickListener {
            viewModel.onEvent(RegisterContract.Event.OnCreateAccountClicked)
        }

        // Sign in link
        binding.tvBtnSignIn.setOnClickListener {
            viewModel.onEvent(RegisterContract.Event.OnSignInClicked)
        }
    }

    private fun setupOtpFields() {
        // Set max length and add listeners
        val otpFields = listOf(
            binding.etOtp1,
            binding.etOtp2,
            binding.etOtp3,
            binding.etOtp4,
            binding.etOtp5,
            binding.etOtp6
        )

        otpFields.forEachIndexed { index, editText ->
            editText.filters = arrayOf(InputFilter.LengthFilter(1))

            editText.addTextChangedListener { text ->
                val digit = text.toString()
                when (index) {
                    0 -> viewModel.onEvent(RegisterContract.Event.OnOtp1Changed(digit))
                    1 -> viewModel.onEvent(RegisterContract.Event.OnOtp2Changed(digit))
                    2 -> viewModel.onEvent(RegisterContract.Event.OnOtp3Changed(digit))
                    3 -> viewModel.onEvent(RegisterContract.Event.OnOtp4Changed(digit))
                    4 -> viewModel.onEvent(RegisterContract.Event.OnOtp5Changed(digit))
                    5 -> viewModel.onEvent(RegisterContract.Event.OnOtp6Changed(digit))
                }
            }

            // Handle backspace to move to previous field
            editText.setOnKeyListener { _, keyCode, _ ->
                if (keyCode == KeyEvent.KEYCODE_DEL && editText.text?.isEmpty() == true && index > 0) {
                    otpFields[index - 1].requestFocus()
                    true
                } else {
                    false
                }
            }
        }
    }

    private fun setupDepartmentDropdown() {
        val departments = listOf(
            "Engineering",
            "Marketing",
            "Sales",
            "Human Resources",
            "Finance",
            "Operations",
            "Customer Support",
            "Product"
        )

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            departments
        )
        binding.departmentDropdown.setAdapter(adapter)
        binding.departmentDropdown.setOnItemClickListener { _, _, _, _ ->
            val selected = binding.departmentDropdown.text.toString()
            viewModel.onEvent(RegisterContract.Event.OnDepartmentSelected(selected))
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                updateUI(state)
            }
        }
    }

    private fun updateUI(state: RegisterContract.State) {
        // Update loading states
        binding.btnCreateAccount.isEnabled = !state.isLoading && state.isCreateAccountEnabled
        binding.btnCreateAccount.alpha = if (state.isCreateAccountEnabled && !state.isLoading) 1f else 0.5f

        // Update Send OTP button
        binding.btnSentOTP.isEnabled = !state.isSendingOtp
        binding.btnSentOTP.text = if (state.isSendingOtp) "Sending..." else "Send OTP"

        // Update OTP timer
        binding.tvExpireTime.text = state.otpExpiryTime

        // Update resend button
        binding.tvBtnResendCode.isEnabled = state.canResendOtp
        binding.tvBtnResendCode.alpha = if (state.canResendOtp) 1f else 0.5f

        // Show validation errors (you can add TextInputLayout for better error display)
        // For now, using toast for errors
    }

    private fun observeEffects() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.effect.collect { effect ->
                handleEffect(effect)
            }
        }
    }

    private fun handleEffect(effect: RegisterContract.Effect) {
        when (effect) {
            is RegisterContract.Effect.NavigateToLogin -> {
                // Navigate to login screen
                Toast.makeText(requireContext(), "Navigate to Login", Toast.LENGTH_SHORT).show()
                // findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }

            is RegisterContract.Effect.NavigateToHome -> {
                // Navigate to home screen
                Toast.makeText(requireContext(), "Registration Successful!", Toast.LENGTH_SHORT).show()
                // findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
            }

            is RegisterContract.Effect.ShowError -> {
                Toast.makeText(requireContext(), effect.message, Toast.LENGTH_LONG).show()
            }

            is RegisterContract.Effect.ShowToast -> {
                Toast.makeText(requireContext(), effect.message, Toast.LENGTH_SHORT).show()
            }

            is RegisterContract.Effect.FocusOtpField -> {
                focusOtpField(effect.fieldIndex)
            }

            is RegisterContract.Effect.ShowPasswordRequirements -> {
                showPasswordRequirementsDialog()
            }
        }
    }

    private fun focusOtpField(fieldIndex: Int) {
        when (fieldIndex) {
            1 -> binding.etOtp1.requestFocus()
            2 -> binding.etOtp2.requestFocus()
            3 -> binding.etOtp3.requestFocus()
            4 -> binding.etOtp4.requestFocus()
            5 -> binding.etOtp5.requestFocus()
            6 -> binding.etOtp6.requestFocus()
        }
    }

    private fun showPasswordRequirementsDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Password Requirements")
            .setMessage(
                """
                Your password must contain:
                • At least 8 characters
                • At least one uppercase letter (A-Z)
                • At least one lowercase letter (a-z)
                • At least one number (0-9)
                """.trimIndent()
            )
            .setPositiveButton("Got it") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}