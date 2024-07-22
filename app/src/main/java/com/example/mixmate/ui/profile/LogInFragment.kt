package com.example.mixmate.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mixmate.databinding.FragmentLogInBinding


class LogInFragment : Fragment() {

    private var _binding: FragmentLogInBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLogInBinding.inflate(inflater,container, false)
        val root: View = binding.root

        // NOTE: TO DO
        binding.loginButton.setOnClickListener{
            Toast.makeText(context, "to be implemented", Toast.LENGTH_SHORT).show()
        }
        binding.forgotPasswordText.setOnClickListener{
            Toast.makeText(context, "to be implemented", Toast.LENGTH_SHORT).show()
        }
        return root
    }
}