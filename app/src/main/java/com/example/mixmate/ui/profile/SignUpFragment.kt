package com.example.mixmate.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.mixmate.R
import com.example.mixmate.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container,false)
        val root: View = binding.root

        val email = binding.editTextText.text
        val password = binding.editTextTextPassword.text
        val confirmPassword = binding.editTextTextConfirmPassword.text

        binding.signUpButton.setOnClickListener {
            val duration = Toast.LENGTH_SHORT
            val toastText = if (email.isEmpty()) {
                "please enter your email!"
            } else if (password.isEmpty()) {
                "please enter a password!"
            } else if (password != confirmPassword) {
                "passwords do not match!"
            } else {
                // NOTE: TO DO
                "thanks for trying to sign up! It just needs to be implemented now :)"
            }

            Toast.makeText(context, toastText, duration).show()
        }

        binding.toLogInText.setOnClickListener {
            Navigation.findNavController(root).navigate(R.id.action_sign_up_to_log_in)
        }

        return root
    }
}