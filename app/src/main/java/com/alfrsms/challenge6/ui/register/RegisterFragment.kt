package com.alfrsms.challenge6.ui.register

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.alfrsms.challenge6.R
import com.alfrsms.challenge6.data.local.preference.PreferenceUser
import com.alfrsms.challenge6.databinding.FragmentRegisterBinding
import com.alfrsms.challenge6.ui.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegister.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        if (validateInput()) {
            val id = (1..10000).random()
            val username = binding.etUsername.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val user = PreferenceUser(
                id = id,
                username = username,
                email = email,
                password = password
            )
            viewModel.registerUser(user)
            Log.d("register", "$id and $username and pass $password")
            navigateToLogin()
        }
    }

    private fun navigateToLogin() {
        val option = NavOptions.Builder()
            .setPopUpTo(R.id.registerFragment, true)
            .build()
        val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
        findNavController().navigate(action, option)
        Toast.makeText(context, "Register Success", Toast.LENGTH_SHORT).show()
    }

    private fun validateInput(): Boolean {
        var isValid = true
        val username = binding.etUsername.text.toString()
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val confirmPassword = binding.etConfirmPassword.text.toString()

        if (username.isEmpty()) {
            isValid = false
            binding.etUsername.error = "Username or password must not be empty"
        }
        if (email.isEmpty()) {
            isValid = false
            binding.etEmail.error = "Email must not be empty"
        }
        if (password.isEmpty()) {
            isValid = false
            Toast.makeText(requireContext(), "Password must not be empty", Toast.LENGTH_SHORT).show()
        }
        if (confirmPassword.isEmpty()) {
            isValid = false
            Toast.makeText(requireContext(), "Confirm password must not be empty", Toast.LENGTH_SHORT).show()
        }
        if (password != confirmPassword) {
            isValid = false
            Toast.makeText(requireContext(), "Password does not match!", Toast.LENGTH_SHORT).show()
        }
        return isValid
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}