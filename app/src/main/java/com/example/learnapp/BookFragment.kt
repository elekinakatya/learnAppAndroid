package com.example.learnapp

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.learnapp.data.data
import com.example.learnapp.list.MyAdapter
import com.example.learnapp.list.SharedViewModel


class BookFragment : Fragment(R.layout.fragment_book) {
    private val viewModel: SharedViewModel by activityViewModels()
    private lateinit var titleTextView: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var topicsTextView: TextView // Текстовое поле для выбранных тем
    private lateinit var testButton: Button // Кнопка для теста

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_book, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleTextView = view.findViewById(R.id.ind)
        progressBar = view.findViewById(R.id.progress)
        topicsTextView = view.findViewById(R.id.textTem) // Инициализируем TextView для тем
        testButton = view.findViewById(R.id.buttonTest) // Инициа

        // Наблюдаем за изменениями в ViewModel
        viewModel.selectedLanguage.observe(viewLifecycleOwner) { language ->
            titleTextView.text = language // Устанавливаем текст заголовка
            progressBar.visibility = View.VISIBLE // Показываем прогресс-бар
        }

        // Наблюдаем за изменениями прогресса
        viewModel.progress.observe(viewLifecycleOwner) { progress ->
            // Обновляем значение прогресс-бара
            progressBar.progress = progress

            // Скрываем прогресс-бар, когда достигнут максимум (например, 10 тем)
            if (progress >= 8) {
                progressBar.visibility =
                    View.GONE // Скрываем прогресс-бар, если достигнуто максимальное значение
            }
        }
        viewModel.selectedTopics.observe(viewLifecycleOwner) { topics ->
            // Обновляем текстовое поле с выбранными темами
            topicsTextView.text = topics.joinToString("\n") { it.name }

            // Показываем кнопку, если есть выбранные темы
            testButton.visibility = if (topics.isNotEmpty()) View.VISIBLE else View.GONE
        }

        // Настройка обработчика для кнопки теста
        testButton.setOnClickListener {
            // Запускаем тест по последней пройденной теме
            viewModel.lastCompletedTopic?.let { topic ->
                val testFragment =
                    QuizFragment.newInstance(topic.documentId) // Передаем documentId последней темы
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.container, testFragment) // Заменяем текущий фрагмент на тестовый
                    .addToBackStack(null) // Добавляем в стек возврата
                    .commit() // Выполняем транзакцию
            } ?: run {
                Toast.makeText(requireContext(), "Нет доступной темы для теста", Toast.LENGTH_SHORT).show() // Сообщение об ошибке, если тема отсутствует
            }
        }
    }
}


