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

    private lateinit var data: data // Убедитесь, что класс Data реализует Parcelable

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            data = it.getParcelable(ARG_DATA) ?: return
        }

        // Форматирование текста: замена двух или более пробелов на новую строку
        view.findViewById<TextView>(R.id.textViewName).text = formatText(data.name)
        view.findViewById<TextView>(R.id.textViewAnnotation).text = formatText(data.anat)
        view.findViewById<TextView>(R.id.textViewInfo).text = formatText(data.info)

        val pythonImage = view.findViewById<ImageView>(R.id.back_button)
        pythonImage.setOnClickListener {
            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.container, PythonFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        val takeQuizButton = view.findViewById<Button>(R.id.buttonTakeQuiz)
        takeQuizButton.setOnClickListener {
            val quizFragment = QuizFragment.newInstance(data.documentId) // Передаем documentId
            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.container, quizFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }


    private fun formatText(input: String): String {
        // Заменяем два или более пробелов на новую строку
        return input.replace(Regex("\\s{2,}"), "\n")
    }
}

//class InfoFragment : Fragment(R.layout.fragment_info) {
//
//    private lateinit var data: data // Убедитесь, что класс Data реализует Parcelable
//
//    companion object {
//        private const val ARG_DATA = "data"
//
//        fun newInstance(dataItem: data): InfoFragment {
//            val fragment = InfoFragment()
//            val bundle = Bundle()
//            bundle.putParcelable(ARG_DATA, dataItem)
//            fragment.arguments = bundle
//            return fragment
//        }
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.fragment_info, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        arguments?.let {
//            data = it.getParcelable(ARG_DATA) ?: return
//        }
//
//        // Форматирование текста: замена двух или более пробелов на новую строку
//        view.findViewById<TextView>(R.id.textViewName).text = formatText(data.name)
//        view.findViewById<TextView>(R.id.textViewAnnotation).text = formatText(data.anat)
//        view.findViewById<TextView>(R.id.textViewInfo).text = formatText(data.info)
//
//        val pythonImage = view.findViewById<ImageView>(R.id.back_button)
//        pythonImage.setOnClickListener {
//            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
//            fragmentTransaction.replace(R.id.container, PythonFragment())
//            fragmentTransaction.addToBackStack(null)
//            fragmentTransaction.commit()
//        }
//        val takeQuizButton = view.findViewById<Button>(R.id.buttonTakeQuiz)
//        takeQuizButton.setOnClickListener {
//            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
//            fragmentTransaction.replace(R.id.container, QuizFragment()) // Замените QuizFragment на ваш реальный класс фрагмента
//            fragmentTransaction.addToBackStack(null)
//            fragmentTransaction.commit()
//        }
//    }
//
//    private fun formatText(input: String): String {
//        // Заменяем два или более пробелов на новую строку
//        return input.replace(Regex("\\s{2,}"), "\n")
//    }
//
//}
