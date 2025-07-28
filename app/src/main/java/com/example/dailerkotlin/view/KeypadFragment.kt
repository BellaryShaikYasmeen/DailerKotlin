package com.example.dailerkotlin.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dailerkotlin.databinding.FragmentKeypadBinding
import com.example.dailerkotlin.viewmodel.PhoneViewModel

class KeypadFragment : Fragment() {
    private var _binding: FragmentKeypadBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PhoneViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentKeypadBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[PhoneViewModel::class.java]
        setupUI()
        return binding.root
    }

    private fun setupUI() {
        val numberButtons = listOf(
            binding.btn0 to "0", binding.btn1 to "1", binding.btn2 to "2", binding.btn3 to "3",
            binding.btn4 to "4", binding.btn5 to "5", binding.btn6 to "6", binding.btn7 to "7",
            binding.btn8 to "8", binding.btn9 to "9", binding.btnStar to "*", binding.btnHash to "#"
        )

        numberButtons.forEach { (button, value) ->
            button.setOnClickListener { viewModel.appendNumber(value) }
        }

        binding.btnDelete.setOnClickListener { viewModel.deleteLast() }
        binding.btnDelete.setOnLongClickListener {
            viewModel.clearAll()
            true
        }

        binding.btnDial.setOnClickListener {
            // TODO: Trigger actual dial intent or display a toast
        }

        viewModel.phoneNumber.observe(viewLifecycleOwner) {
            if(it.length<=10)
            binding.txtPhone.text = it
            binding.btnDial.isEnabled = it.length==10
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
