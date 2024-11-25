package com.example.learnapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class ResultFragment : Fragment(R.layout.fragment_result) {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Получение данных о результатах теста
        val score = arguments?.getInt("SCORE") ?: 0
        val totalQuestions = arguments?.getInt("TOTAL_QUESTIONS") ?: 0

        // Отображение результатов
        view.findViewById<TextView>(R.id.result_text).text = "Ваш результат: $score из $totalQuestions"

        // Кнопки для перехода к другим фрагментам
        view.findViewById<Button>(R.id.button_next_topic).setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container, PythonFragment())
                .addToBackStack(null)
                .commit()
        }

        view.findViewById<Button>(R.id.button_my_learning).setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container, BookFragment())
                .addToBackStack(null)
                .commit()
        }

        view.findViewById<Button>(R.id.button_main_menu).setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container, MenuFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}
