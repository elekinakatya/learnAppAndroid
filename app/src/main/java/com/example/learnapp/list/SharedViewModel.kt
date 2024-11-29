package com.example.learnapp.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.learnapp.data.data

class SharedViewModel : ViewModel() {
    private val _selectedLanguage = MutableLiveData<String>()
    val selectedLanguage: LiveData<String> get() = _selectedLanguage

    private val _progress = MutableLiveData<Int>(0)
    val progress: LiveData<Int> get() = _progress

    private val _selectedTopics = MutableLiveData<MutableList<data>>(mutableListOf()) // Список выбранных тем
    val selectedTopics: MutableLiveData<MutableList<data>> get() = _selectedTopics

    var lastCompletedTopic: data? = null // Переменная для хранения последней пройденной темы

    fun selectLanguage(language: String) {
        _selectedLanguage.value = language
    }

    fun updateProgress(currentProgress: Int) {
        _progress.value = currentProgress
    }
    fun removeSelectedTopic(topic: data) {
        val currentList = _selectedTopics.value?.toMutableList() ?: mutableListOf()
        currentList.removeAll { it.documentId == topic.documentId }
        _selectedTopics.value = currentList
    }
    fun getSortedSelectedTopics(): List<data> {
        return _selectedTopics.value?.sortedBy { it.documentId } ?: emptyList()
    }


    fun addSelectedTopic(topic: data) {
        // Проверяем, существует ли тема уже в списке
        if (_selectedTopics.value?.any { it.documentId == topic.documentId } != true) {
            _selectedTopics.value?.add(topic) // Добавляем тему только если ее нет в списке
            _selectedTopics.value = _selectedTopics.value // Триггерим обновление LiveData
            lastCompletedTopic = topic // Обновляем последнюю пройденную тему
        }
    }
}