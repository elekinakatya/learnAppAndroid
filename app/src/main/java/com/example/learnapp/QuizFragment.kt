package com.example.learnapp

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.learnapp.data.Question
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore

class QuizFragment : Fragment(R.layout.fragment_quiz) {

    private lateinit var questionsList: MutableList<Question>
    private lateinit var db: FirebaseFirestore
    private lateinit var documentId: String
    private var currentQuestionIndex = 0
    private var selectedAnswer: String? = null // Хранит выбранный ответ
    private var correctAnswersCount = 0 // Количество правильных ответов
    private lateinit var progressIndicator: LinearProgressIndicator // Прогресс-индикатор
    companion object {
        private const val ARG_DOCUMENT_ID = "documentId"

        fun newInstance(documentId: String): QuizFragment {
            return QuizFragment().apply {
                arguments = Bundle().apply { putString(ARG_DOCUMENT_ID, documentId) }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_quiz, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = FirebaseFirestore.getInstance()
        questionsList = mutableListOf()
        progressIndicator = view.findViewById(R.id.progress_indif) // Инициализация прогресс-индикатора

        documentId = arguments?.getString(ARG_DOCUMENT_ID) ?: return
        fetchQuestionsFromFirestore(view)

        val backButton = view.findViewById<ImageView>(R.id.back_button)
        backButton.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container, PythonFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun fetchQuestionsFromFirestore(view: View) {
        db.collection("learn").document(documentId).collection("ques").get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val question = document.toObject(Question::class.java)
                    questionsList.add(question)
                }
                if (questionsList.isNotEmpty()) {
                    displayQuestion(view, questionsList[currentQuestionIndex])
                } else {
                    Toast.makeText(requireContext(), "Вопросы не найдены", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                Log.d("QuizFragment", "Ошибка получения документов: ", exception)
            }
    }

    private fun displayQuestion(view: View, question: Question) {
        view.findViewById<TextView>(R.id.ques_text).text = question.question
        val buttons = listOf(
            view.findViewById<Button>(R.id.button1),
            view.findViewById<Button>(R.id.button2),
            view.findViewById<Button>(R.id.button3),
            view.findViewById<Button>(R.id.button4)
        )

        buttons.forEachIndexed { index, button ->
            button.text = question.answer.getOrNull(index) ?: ""
            button.setBackgroundColor(Color.TRANSPARENT)
        }

        buttons.forEach { button ->
            button.setOnClickListener {
                selectedAnswer = button.text.toString() // Запоминаем выбранный ответ
                buttons.forEach { b -> b.setBackgroundColor(Color.TRANSPARENT) }
                button.setBackgroundColor(Color.parseColor("#D5C5ED"))
            }
        }
        view.findViewById<Button>(R.id.buttonQuiz).setOnClickListener {
            if (selectedAnswer == null) {
                Toast.makeText(requireContext(), "Пожалуйста, выберите ответ!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (selectedAnswer == question.yes) {
                Toast.makeText(requireContext(), "Правильный ответ!", Toast.LENGTH_SHORT).show()
                correctAnswersCount++ // Увеличиваем счетчик правильных ответов
                currentQuestionIndex++
                updateProgressIndicator() // Обновление прогресса

                if (currentQuestionIndex < questionsList.size) {
                    selectedAnswer = null // Сбрасываем выбранный ответ
                    displayQuestion(view, questionsList[currentQuestionIndex])
                } else {
                    // Переход на фрагмент результатов
                    val resultFragment = ResultFragment().apply {
                        arguments = Bundle().apply {
                            putInt("SCORE", correctAnswersCount) // Передаем количество правильных ответов
                            putInt("TOTAL_QUESTIONS", questionsList.size) // Общее количество вопросов
                        }
                    }
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.container, resultFragment)
                        .addToBackStack(null)
                        .commit()
                }
            } else {
                Toast.makeText(requireContext(), "Ответ неверный! Попробуйте еще раз.", Toast.LENGTH_SHORT).show()
            }
        }



        // Обновление индикатора вопросов
        updateQuestionIndicator(view)
    }
    private fun calculateScore(): Int {
        // Здесь реализуйте логику подсчета баллов
        // Например, если вы храните количество правильных ответов
        return questionsList.count { it.yes == selectedAnswer } // Пример подсчета
    }

    private fun updateProgressIndicator() {
        val progress = ((currentQuestionIndex) * 100) / questionsList.size // Рассчитываем прогресс
        progressIndicator.setProgress(progress) // Устанавливаем прогресс
    }

    private fun updateQuestionIndicator(view: View) {
        val quesIndicator = view.findViewById<TextView>(R.id.ques_indicator)
        quesIndicator.text = "Вопрос ${currentQuestionIndex + 1}/${questionsList.size}"
    }
}
