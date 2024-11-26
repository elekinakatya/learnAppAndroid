package com.example.learnapp

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.learnapp.data.data
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class InfoFragment : Fragment(R.layout.fragment_info) {

    private lateinit var data: data

    companion object {
        private const val ARG_DATA = "data"

        fun newInstance(dataItem: data): InfoFragment {
            val fragment = InfoFragment()
            val bundle = Bundle()
            bundle.putParcelable(ARG_DATA, dataItem)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            data = it.getParcelable(ARG_DATA) ?: return
        }

        // Форматирование текста
        view.findViewById<TextView>(R.id.textViewName).text = formatText(data.name)
        view.findViewById<TextView>(R.id.textViewAnnotation).text = formatText(data.anat)
        view.findViewById<TextView>(R.id.textViewInfo).text = formatText(data.info)

        view.findViewById<ImageView>(R.id.back_button).setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        view.findViewById<Button>(R.id.buttonTakeQuiz).setOnClickListener {
            val quizFragment = QuizFragment.newInstance(data.documentId)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container, quizFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun formatText(input: String): String {
        return input.replace(Regex("\\s{2,}"), "\n")
    }
}


