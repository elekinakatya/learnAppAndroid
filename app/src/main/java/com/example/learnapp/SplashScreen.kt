package com.example.learnapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class SplashScreen : Fragment(R.layout.fragment_splash_screen) {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        // Отложенный переход на главный экран через 2 секунды
        view.postDelayed({
            // Переход к MenuFragment после задержки
            replaceFragment(MenuFragment())
        }, 2000) // 2000 миллисекунд = 2 секунды
    }

    private fun replaceFragment(fragment: Fragment) {
        // Замена фрагмента
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}