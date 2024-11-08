package com.example.learnapp
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.learnapp.AdviceFragment
import com.example.learnapp.BookFragment
import com.example.learnapp.MenuFragment
import com.example.learnapp.ProfileFragment
import com.example.learnapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity: AppCompatActivity() {

    lateinit var bottomNav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadFragment(MenuFragment())
        bottomNav = findViewById(R.id.bottomNav) as BottomNavigationView
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.profile -> {
                    loadFragment(ProfileFragment())
                    true
                }
                R.id.book -> {
                    loadFragment(BookFragment())
                    true
                }
                R.id.menu -> {
                    loadFragment(MenuFragment())
                    true
                }
                R.id.advice-> {
                    loadFragment(AdviceFragment())
                    true
                }

                else -> {
                    false
                }
            }
        }
    }
    private fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,fragment)
        transaction.commit()
    }

}
