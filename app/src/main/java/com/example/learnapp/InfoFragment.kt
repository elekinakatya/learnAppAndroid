package com.example.learnapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.learnapp.data.data

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

        view.findViewById<TextView>(R.id.textViewName).text = data.name
        view.findViewById<TextView>(R.id.textViewAnnotation).text = data.anat
        view.findViewById<TextView>(R.id.textViewInfo).text = data.info
        val pythonImage = view.findViewById<ImageView>(R.id.back_button)
        pythonImage.setOnClickListener {
            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.container, PythonFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }
}