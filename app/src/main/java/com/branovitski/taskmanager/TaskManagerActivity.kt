package com.branovitski.taskmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.branovitski.taskmanager.databinding.ActivityTaskManagerBinding
import com.branovitski.taskmanager.ui.allnotes.AllNotesFragment
import com.github.terrakok.cicerone.*
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TaskManagerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTaskManagerBinding

    private val navigator = AppNavigator(this, R.id.container)

    @Inject
    lateinit var navigatorHolder: NavigatorHolder


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskManagerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navigator.applyCommands(arrayOf<Command>(Replace(Screens.AllNotes())))
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}