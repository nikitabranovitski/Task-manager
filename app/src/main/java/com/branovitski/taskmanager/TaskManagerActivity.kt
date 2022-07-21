package com.branovitski.taskmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.branovitski.taskmanager.databinding.ActivityTaskManagerBinding
import com.branovitski.taskmanager.ui.allnotes.AllNotesFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskManagerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTaskManagerBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskManagerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().replace(R.id.container, AllNotesFragment())
            .commit()
    }
}