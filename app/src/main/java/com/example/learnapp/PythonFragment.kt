package com.example.learnapp

import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.learnapp.data.data
import com.example.learnapp.list.MyAdapter
import com.example.learnapp.list.SharedViewModel
import com.google.firebase.firestore.FirebaseFirestore
class PythonFragment : Fragment(R.layout.fragment_python), MyAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyAdapter
    private val viewModel: SharedViewModel by activityViewModels()
    private val db = FirebaseFirestore.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = MyAdapter(emptyList(), this)
        recyclerView.adapter = adapter

        fetchDataFromFirestore()

        val pythonImage = view.findViewById<ImageView>(R.id.back_button)
        pythonImage.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
    private fun fetchDataFromFirestore() {
        db.collection("learn").get()
            .addOnSuccessListener { documents ->
                val dataList = mutableListOf<data>()
                var firstTopic = true // Флаг для первой темы
                for (document in documents) {
                    val dataItem = document.toObject(data::class.java)
                    // Устанавливаем завершенность для первой темы
                    if (firstTopic) {
                        dataItem.isCompleted = true
                        firstTopic = false
                    } else {
                        dataItem.isCompleted = false
                    }
                    dataList.add(dataItem)
                }
                adapter.updateData(dataList)
            }
            .addOnFailureListener { exception ->
                Log.d("PythonFragment", "Error getting documents: ", exception)
            }
    }

    override fun onItemClick(dataItem: data) {
        val isTopicAlreadySelected = viewModel.selectedTopics.value?.any { it.documentId == dataItem.documentId } == true

        if (!isTopicAlreadySelected) {
            val currentProgress = viewModel.progress.value ?: 0
            val newProgress = currentProgress + 1 // Увеличиваем прогресс на 1, если тема новая

            // Обновляем прогресс в ViewModel
            viewModel.updateProgress(newProgress)

            // Добавляем выбранную тему в ViewModel
            viewModel.addSelectedTopic(dataItem)
        }

        // Переход к InfoFragment с передачей данных, включая documentId
        val infoFragment = InfoFragment.newInstance(dataItem.copy(documentId = dataItem.documentId))
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container, infoFragment)
            .addToBackStack(null)
            .commit()
    }
}


