package com.example.searchablespinnerdialog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.searchablespinnerdialog.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.tvSpinner.setOnClickListener {
            SearchableSpinnerDialog(this@MainActivity, arrayListOf("1", "2", "3"), object : SearchableSpinnerDialog.SearchableSpinnerListListener {
                override fun onItemClickListener(name: String) {
                    binding.tvSpinner.text = name
                    Log.i("TAG", "onItemClickListener: $name.")
                }
            }).show()
        }
    }
}