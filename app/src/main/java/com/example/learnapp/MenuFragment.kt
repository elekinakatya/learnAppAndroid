package com.example.learnapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.activityViewModels
import android.widget.Toast
import com.example.learnapp.list.SharedViewModel

class MenuFragment : Fragment(R.layout.fragment_menu) {
    private val viewModel: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Проверка наличия элемента
        val pythonImage = view.findViewById<ImageView>(R.id.python_img)

        if (pythonImage == null) {
            Toast.makeText(requireContext(), "ImageView not found", Toast.LENGTH_SHORT).show()
            return
        }

        pythonImage.setOnClickListener {
            // Обновляем состояние ViewModel
            viewModel.selectLanguage("Python")

            // Переход к PythonFragment
            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.container, PythonFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }
}