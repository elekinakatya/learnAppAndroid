package com.example.learnapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

class MenuFragment:Fragment(R.layout.fragment_menu) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pythonImage = view.findViewById<ImageView>(R.id.python_img)
        pythonImage.setOnClickListener {
            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.container, PythonFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }
}