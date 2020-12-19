package com.example.searchablespinnerdialog

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.searchablespinnerdialog.SearchableSpinnerDialog.SearchableSpinnerListListener
import com.example.searchablespinnerdialog.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private var xDown = 100f
    private var yDown = 100f

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.tvSpinner.setOnTouchListener(touchListener2)

        viewPosX[binding.tvSpinner.hashCode().toString()] = binding.tvSpinner.x
        viewPosY[binding.tvSpinner.hashCode().toString()] = binding.tvSpinner.y

        binding.tvSpinner.setOnClickListener {
            SearchableSpinnerDialog(
                    this@MainActivity,
                    arrayListOf("1", "2", "3"),
                    object : SearchableSpinnerListListener {
                        override fun onItemClickListener(name: String) {

                            val newTv = TextView(this@MainActivity)
                                    .apply {
                                        text = name
                                        layoutParams = LinearLayout.LayoutParams(
                                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                                LinearLayout.LayoutParams.WRAP_CONTENT)
                                        if(Random.nextInt(0, 5) == 1)
                                        setTextColor(Color.parseColor("#FFD700"))

                                        textSize = 48f
                                        x = xDown
                                        y = yDown
                                        viewPosX[hashCode().toString()] = x
                                        viewPosY[hashCode().toString()] = y
                                        setOnTouchListener(touchListener2)
                                        setOnClickListener {
                                            Toast.makeText(this@MainActivity,
                                                    "Aaah!",
                                                    Toast.LENGTH_SHORT)
                                                    .show()
                                        }
                                    }

                            binding.root.addView(newTv)

                            Log.i("TAG", "onItemClickListener: $name.")
                        }
                    }
            ).show()
        }
    }

    private val viewPosX: MutableMap<String,  Float> = HashMap()
    private val viewPosY: MutableMap<String,  Float> = HashMap()

    // https://www.youtube.com/watch?v=9nwOLkmdKe8
    @SuppressLint("ClickableViewAccessibility")
    val touchListener2 = View.OnTouchListener { view, motionEvent ->
        when (motionEvent.actionMasked) {

            MotionEvent.ACTION_DOWN -> {
                viewPosX[view.hashCode().toString()] = view.x - motionEvent.rawX
                viewPosY[view.hashCode().toString()] = view.y - motionEvent.rawY
            }

            MotionEvent.ACTION_MOVE -> {
               view.animate()
                       .x(motionEvent.rawX + viewPosX[view.hashCode().toString()]!!)
                       .y(motionEvent.rawY + viewPosY[view.hashCode().toString()]!!)
                       .setDuration(0)
                       .start()
            }

            MotionEvent.ACTION_UP -> {
                if (view.hashCode() == binding.tvSpinner.hashCode()){
                    view.animate()
                        .x(binding.glStart.left.toFloat())
                        .y(binding.glTop.top.toFloat())
                        .setDuration(250)
                        .start()
                }
            }

        }

        return@OnTouchListener false
    }
}

