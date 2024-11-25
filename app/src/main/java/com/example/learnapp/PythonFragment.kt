package com.example.learnapp

import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.learnapp.data.data
import com.example.learnapp.list.MyAdapter
import com.google.firebase.firestore.FirebaseFirestore
class PythonFragment : Fragment(R.layout.fragment_python), MyAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyAdapter
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
            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.container, MenuFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }

    private fun fetchDataFromFirestore() {
        db.collection("learn").get()
            .addOnSuccessListener { documents ->
                val dataList = mutableListOf<data>()
                for (document in documents) {
                    val dataItem = document.toObject(data::class.java)
                    // Добавление документа в конец списка для сохранения порядка
                    dataList.add(dataItem)
                }
                // Обновляем адаптер с полученными данными в том порядке, в котором они были извлечены
                adapter.updateData(dataList)
            }
            .addOnFailureListener { exception ->
                Log.d("PythonFragment", "Error getting documents: ", exception)
            }
    }

    override fun onItemClick(dataItem: data) {
        // Переход к InfoFragment с передачей данных, включая documentId
        val infoFragment = InfoFragment.newInstance(dataItem.copy(documentId = dataItem.documentId))
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container, infoFragment)
            .addToBackStack(null)
            .commit()
    }
}

//class PythonFragment : Fragment(R.layout.fragment_python), MyAdapter.OnItemClickListener {
//
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var adapter: MyAdapter
//    private val db = FirebaseFirestore.getInstance()
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        recyclerView = view.findViewById(R.id.recyclerView)
//        recyclerView.layoutManager = LinearLayoutManager(context)
//        adapter = MyAdapter(emptyList(), this)
//        recyclerView.adapter = adapter
//
//        fetchDataFromFirestore()
//
//        val pythonImage = view.findViewById<ImageView>(R.id.back_button)
//        pythonImage.setOnClickListener {
//            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
//            fragmentTransaction.replace(R.id.container, MenuFragment())
//            fragmentTransaction.addToBackStack(null)
//            fragmentTransaction.commit()
//        }
//    }
//
//    private fun fetchDataFromFirestore() {
//        db.collection("learn").get()
//            .addOnSuccessListener { documents ->
//                val dataList = mutableListOf<data>()
//                for (document in documents) {
//                    val dataItem = document.toObject(data::class.java)
//                    // Добавление документа в конец списка для сохранения порядка
//                    dataList.add(dataItem)
//                }
//                // Обновляем адаптер с полученными данными в том порядке, в котором они были извлечены
//                adapter.updateData(dataList)
//            }
//            .addOnFailureListener { exception ->
//                Log.d("PythonFragment", "Error getting documents: ", exception)
//            }
//    }
//
//    override fun onItemClick(dataItem: data) {
//        // Переход к InfoFragment с передачей данных
//        val infoFragment = InfoFragment.newInstance(dataItem)
//        requireActivity().supportFragmentManager.beginTransaction()
//            .replace(R.id.container, infoFragment)
//            .addToBackStack(null)
//            .commit()
//    }
//}

